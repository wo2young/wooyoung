select * from user_t;

 DELETE FROM user_t;
 COMMIT;
 
 INSERT INTO USER_T (LOGIN_ID, PASSWORD, NAME, USER_ROLE, CREATED_AT)
VALUES ('ADMIN1',   'admin123',   'System Admin', 'ADMIN',   SYSTIMESTAMP);

INSERT INTO USER_T (LOGIN_ID, PASSWORD, NAME, USER_ROLE, CREATED_AT)
VALUES ('MANAGER1', 'manager123', 'Line Manager', 'MANAGER', SYSTIMESTAMP);

INSERT INTO USER_T (LOGIN_ID, PASSWORD, NAME, USER_ROLE, CREATED_AT)
VALUES ('WORKER1',  'worker123',  'Alice Worker', 'WORKER',  SYSTIMESTAMP);

INSERT INTO USER_T (LOGIN_ID, PASSWORD, NAME, USER_ROLE, CREATED_AT)
VALUES ('WORKER2',  'worker123',  'Bob Worker',   'WORKER',  SYSTIMESTAMP);

-- 네가 쓰던 테스트 계정 형태도 추가하고 싶으면 (예시)
INSERT INTO USER_T (LOGIN_ID, PASSWORD, NAME, USER_ROLE, CREATED_AT)
VALUES ('1234',      '1234',        'qwer',          'ADMIN',   SYSTIMESTAMP);

COMMIT;

CREATE TABLE USER_T_BAK AS SELECT * FROM USER_T;

SELECT user_id, login_id, password
FROM USER_T
WHERE password IS NOT NULL
  AND password NOT LIKE '$2%';
  
DROP TABLE USER_T_BAK;

select * from code_master;

select * from code_detail;

-- 품질 불량 테이블 예시
-- defect_id, result_id, defect_code, defect_qty, created_at, created_by
INSERT INTO QUALITY_DEFECT (DEFECT_ID, RESULT_ID, DEFECT_CODE, DEFECT_QTY, CREATED_AT, CREATED_BY)
VALUES (1, 101, 'DEF-001', 5, SYSDATE, 1);

INSERT INTO QUALITY_DEFECT (DEFECT_ID, RESULT_ID, DEFECT_CODE, DEFECT_QTY, CREATED_AT, CREATED_BY)
VALUES (2, 101, 'DEF-002', 2, SYSDATE, 1);

INSERT INTO QUALITY_DEFECT (DEFECT_ID, RESULT_ID, DEFECT_CODE, DEFECT_QTY, CREATED_AT, CREATED_BY)
VALUES (3, 102, 'DEF-005', 3, SYSDATE, 2);

INSERT INTO QUALITY_DEFECT (DEFECT_ID, RESULT_ID, DEFECT_CODE, DEFECT_QTY, CREATED_AT, CREATED_BY)
VALUES (4, 103, 'DEF-006', 1, SYSDATE, 2);

select * from quality_defect;

-- COKE (PCD-001) 생산실적 1번에서 뚜껑불량 DEF-001, 7개 발생
INSERT INTO QUALITY_DEFECT (DEFECT_ID, RESULT_ID, DETAIL_CODE, QUANTITY, REGISTERED_BY, CREATED_AT)
VALUES (10, 1, 'DEF-001', 7, 1, SYSTIMESTAMP);

select * from PRODUCTION_RESULT;

select * from code_detail;

MERGE INTO CODE_MASTER m
USING (SELECT 'DEFECT' CODE_ID, '불량 유형' CODE_NAME FROM dual) s
ON (m.CODE_ID = s.CODE_ID)
WHEN NOT MATCHED THEN
  INSERT (CODE_ID, CODE_NAME) VALUES (s.CODE_ID, s.CODE_NAME);

-- 디테일(중복 방지 MERGE)
MERGE INTO CODE_DETAIL d
USING (
  SELECT 'DEF-001' DETAIL_CODE, 'DEFECT' CODE_ID, '뚜껑 불량' CODE_DNAME, 'Y' IS_ACTIVE FROM dual UNION ALL
  SELECT 'DEF-002','DEFECT','라벨 불량','Y' FROM dual UNION ALL
  SELECT 'DEF-003','DEFECT','내용량 부족','Y' FROM dual UNION ALL
  SELECT 'DEF-004','DEFECT','색상 이상','Y' FROM dual UNION ALL
  SELECT 'DEF-005','DEFECT','탄산 부족','Y' FROM dual UNION ALL
  SELECT 'DEF-006','DEFECT','병 파손','Y' FROM dual
)
ON (d.DETAIL_CODE = s.DETAIL_CODE)
WHEN NOT MATCHED THEN
  INSERT (DETAIL_CODE, CODE_ID, CODE_DNAME, IS_ACTIVE)
  VALUES (s.DETAIL_CODE, s.CODE_ID, s.CODE_DNAME, s.IS_ACTIVE);

commit;

-- 1) 테이블 위치(소유자) 찾기
SELECT owner, table_name
FROM   all_tables
WHERE  UPPER(table_name) = 'USERS';

-- 2) 권한 확인 (현재 계정이 뭘 볼 수 있나)
SELECT table_name, owner, privilege
FROM   all_tab_privs
WHERE  UPPER(table_name) = 'USERS'
AND    grantee = USER;  -- 현재 접속 계정

SELECT
  login_id,
  LENGTH(reset_token)              AS tok_len,
  LENGTH(TRIM(reset_token))        AS tok_len_trim,
  reset_expires,
  CASE WHEN reset_expires > SYSTIMESTAMP THEN 'Y' ELSE 'N' END AS valid,
  -- 분 단위로 남은 시간(숫자): DATE 캐스팅 후 일수 * 1440
  ROUND( (CAST(reset_expires AS DATE) - CAST(SYSTIMESTAMP AS DATE)) * 1440, 1 ) AS minutes_left
FROM user_t
WHERE UPPER(login_id) = UPPER('HYG');

--------------------------------------------------
-- BoardCategory
--------------------------------------------------
CREATE TABLE BoardCategory (
    category_id     NUMBER PRIMARY KEY,
    category_name   VARCHAR2(100) NOT NULL,
    is_active       CHAR(1) DEFAULT 'Y' CHECK (is_active IN ('Y','N')),
    ord             NUMBER
);

CREATE SEQUENCE seq_board_category START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_board_category_pk
BEFORE INSERT ON BoardCategory
FOR EACH ROW
BEGIN
  IF :NEW.category_id IS NULL THEN
    SELECT seq_board_category.NEXTVAL INTO :NEW.category_id FROM dual;
  END IF;
END;
/

--------------------------------------------------
-- BoardPost
--------------------------------------------------
CREATE TABLE BoardPost (
    post_id     NUMBER PRIMARY KEY,
    category_id NUMBER NOT NULL,
    title       VARCHAR2(200) NOT NULL,
    content     CLOB NOT NULL,
    is_notice   CHAR(1) DEFAULT 'N' CHECK (is_notice IN ('Y','N')),
    view_cnt    NUMBER DEFAULT 0,
    like_cnt    NUMBER DEFAULT 0,
    is_deleted  CHAR(1) DEFAULT 'N' CHECK (is_deleted IN ('Y','N')),
    created_by  NUMBER NOT NULL,
    created_at  TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at  TIMESTAMP,
    CONSTRAINT fk_board_post_category FOREIGN KEY (category_id) REFERENCES BoardCategory(category_id),
    CONSTRAINT fk_board_post_user FOREIGN KEY (created_by) REFERENCES user_t(user_id)
);

CREATE SEQUENCE seq_board_post START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_board_post_pk
BEFORE INSERT ON BoardPost
FOR EACH ROW
BEGIN
  IF :NEW.post_id IS NULL THEN
    SELECT seq_board_post.NEXTVAL INTO :NEW.post_id FROM dual;
  END IF;
END;
/

--------------------------------------------------
-- BoardComment
--------------------------------------------------
CREATE TABLE BoardComment (
    comment_id  NUMBER PRIMARY KEY,
    post_id     NUMBER NOT NULL,
    content     CLOB NOT NULL,
    is_deleted  CHAR(1) DEFAULT 'N' CHECK (is_deleted IN ('Y','N')),
    created_by  NUMBER NOT NULL,
    created_at  TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at  TIMESTAMP,
    CONSTRAINT fk_board_comment_post FOREIGN KEY (post_id) REFERENCES BoardPost(post_id),
    CONSTRAINT fk_board_comment_user FOREIGN KEY (created_by) REFERENCES USER_T(user_id)
);

CREATE SEQUENCE seq_board_comment START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_board_comment_pk
BEFORE INSERT ON BoardComment
FOR EACH ROW
BEGIN
  IF :NEW.comment_id IS NULL THEN
    SELECT seq_board_comment.NEXTVAL INTO :NEW.comment_id FROM dual;
  END IF;
END;
/

--------------------------------------------------
-- BoardAttachment
--------------------------------------------------
CREATE TABLE BoardAttachment (
    attach_id   NUMBER PRIMARY KEY,
    post_id     NUMBER NOT NULL,
    orig_name   VARCHAR2(255) NOT NULL,
    save_name   VARCHAR2(255) NOT NULL,
    save_path   VARCHAR2(500) NOT NULL,
    file_size    NUMBER DEFAULT 0,
    content_type VARCHAR2(100),
    uploaded_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    uploaded_by NUMBER NOT NULL,
    CONSTRAINT fk_board_attachment_post FOREIGN KEY (post_id) REFERENCES BoardPost(post_id),
    CONSTRAINT fk_board_attachment_user FOREIGN KEY (uploaded_by) REFERENCES USER_T(user_id)
);

CREATE SEQUENCE seq_board_attachment START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_board_attachment_pk
BEFORE INSERT ON BoardAttachment
FOR EACH ROW
BEGIN
  IF :NEW.attach_id IS NULL THEN
    SELECT seq_board_attachment.NEXTVAL INTO :NEW.attach_id FROM dual;
  END IF;
END;
/

--------------------------------------------------
-- BoardLike
--------------------------------------------------
CREATE TABLE BoardLike (
    post_id    NUMBER NOT NULL,
    user_id    NUMBER NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT pk_board_like PRIMARY KEY (post_id, user_id),
    CONSTRAINT fk_board_like_post FOREIGN KEY (post_id) REFERENCES BoardPost(post_id),
    CONSTRAINT fk_board_like_user FOREIGN KEY (user_id) REFERENCES USER_T(user_id)
);

--------------------------------------------------
-- 인덱스 (검색 성능용)
--------------------------------------------------
CREATE INDEX idx_board_post_category ON BoardPost(category_id, created_at DESC);
CREATE INDEX idx_board_post_deleted ON BoardPost(is_deleted);
CREATE INDEX idx_board_comment_post ON BoardComment(post_id, created_at DESC);
CREATE INDEX idx_board_attachment_post ON BoardAttachment(post_id);

-- 카테고리
INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('공지', 'Y', 1);
INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('자유', 'Y', 2);
INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('질문', 'Y', 3);

-- 작성자(예시) : USER_T 에 로그인 사용자 하나
INSERT INTO USER_T (login_id, password, name, user_role)
VALUES ('4admin', 'secret', '관리자', 'ADMIN');


-- 글 등록 (공지)
INSERT INTO BoardPost (category_id, title, content, is_notice, created_by)
VALUES (1, '시스템 공지', '테스트 본문', 'Y', 1);

-- 글 등록 (일반)
INSERT INTO BoardPost (category_id, title, content, created_by)
VALUES (2, '첫 글', '안녕하세요', 1);

-- 댓글
INSERT INTO BoardComment (post_id, content, created_by)
VALUES (2, '첫 댓글!', 1);

-- 첨부
INSERT INTO BoardAttachment (post_id, orig_name, save_name, save_path, file_size, content_type, uploaded_by)
VALUES (2, 'hello.txt', 'a1b2c3.txt', '/upload/2025/09/11', 12, 'text/plain', 1);

-- 좋아요
INSERT INTO BoardLike (post_id, user_id) VALUES (2, 1);


--SELECT
-- 목록(공지 우선 + 최신순, 삭제 제외)
SELECT *
FROM (
  SELECT p.*,
         ROW_NUMBER() OVER(ORDER BY CASE WHEN p.is_notice='Y' THEN 0 ELSE 1 END, p.created_at DESC) AS rn
  FROM BoardPost p
  WHERE p.is_deleted = 'N'
    AND (:categoryId IS NULL OR p.category_id = :categoryId)
    AND (:keyword   IS NULL OR (p.title LIKE '%'||:keyword||'%' OR DBMS_LOB.INSTR(p.content, :keyword) > 0))
) 
WHERE t.rn BETWEEN :startRow AND :endRow;

-- 상세
SELECT p.*, u.name AS writer_name, c.category_name
FROM BoardPost p
JOIN USER_T u ON u.user_id = p.created_by
JOIN BoardCategory c ON c.category_id = p.category_id
WHERE p.post_id = :postId;

-- 첨부, 댓글
SELECT * FROM BoardAttachment WHERE post_id = :postId ORDER BY uploaded_at;
SELECT cm.*, u.name AS writer_name
FROM BoardComment cm JOIN USER_T u ON u.user_id = cm.created_by
WHERE cm.post_id = :postId AND cm.is_deleted='N'
ORDER BY cm.created_at;

-- 내가 쓴 글
SELECT post_id, title, created_at FROM BoardPost WHERE created_by = :userId AND is_deleted='N' ORDER BY created_at DESC;


-- UPDATE
-- 조회수 +1 (동시성 안전하게)
UPDATE BoardPost SET view_cnt = view_cnt + 1 WHERE post_id = :postId;

-- 글 수정
UPDATE BoardPost
   SET title = :title,
       content = :content,
       category_id = :categoryId,
       is_notice = :isNotice,
       updated_at = SYSTIMESTAMP
 WHERE post_id = :postId
   AND (created_by = :userId OR :isAdmin = 1);

-- 소프트 삭제
UPDATE BoardPost
   SET is_deleted='Y', updated_at=SYSTIMESTAMP
 WHERE post_id = :postId
   AND (created_by = :userId OR :isAdmin = 1);

-- 댓글 등록/삭제
INSERT INTO BoardComment (post_id, content, created_by) VALUES (:postId, :content, :userId);
UPDATE BoardComment SET is_deleted='Y', updated_at=SYSTIMESTAMP
 WHERE comment_id=:commentId AND (created_by=:userId OR :isAdmin=1);

-- 좋아요 토글 (있으면 삭제, 없으면 삽입) - MERGE로 업서트
MERGE INTO BoardLike t
USING (SELECT :postId AS post_id, :userId AS user_id FROM dual) s
ON (t.post_id = s.post_id AND t.user_id = s.user_id)
WHEN NOT MATCHED THEN
  INSERT (post_id, user_id, created_at) VALUES (s.post_id, s.user_id, SYSTIMESTAMP);

-- 좋아요 취소
DELETE FROM BoardLike WHERE post_id=:postId AND user_id=:userId;


-- DELETE
-- 예: 댓글 FK 교체(기존 FK 이름 확인 후 드롭)
ALTER TABLE BoardComment DROP CONSTRAINT fk_board_comment_post;
ALTER TABLE BoardComment
  ADD CONSTRAINT fk_board_comment_post
  FOREIGN KEY (post_id) REFERENCES BoardPost(post_id) ON DELETE CASCADE;

ALTER TABLE BoardAttachment DROP CONSTRAINT fk_board_attachment_post;
ALTER TABLE BoardAttachment
  ADD CONSTRAINT fk_board_attachment_post
  FOREIGN KEY (post_id) REFERENCES BoardPost(post_id) ON DELETE CASCADE;

ALTER TABLE BoardLike DROP CONSTRAINT pk_board_like; -- PK 유지, FK만 교체
ALTER TABLE BoardLike DROP CONSTRAINT fk_board_like_post;
ALTER TABLE BoardLike
  ADD CONSTRAINT fk_board_like_post
  FOREIGN KEY (post_id) REFERENCES BoardPost(post_id) ON DELETE CASCADE;

-- 실제 생성된 사용자 ID 확인
SELECT user_id, login_id, name, user_role FROM USER_T ORDER BY user_id;

-- 게시글 FK 유효성 점검
SELECT post_id, created_by 
FROM BoardPost 
WHERE created_by NOT IN (SELECT user_id FROM USER_T);

SELECT COUNT(*) AS cnt FROM user_tables WHERE table_name = 'BOARDCATEGORY';

SELECT category_id, category_name, is_active, ord
FROM BoardCategory
ORDER BY category_id;

INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('공지', 'Y', 1);
INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('자유', 'Y', 2);
INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('질문', 'Y', 3);

-- 부모/루트/깊이
ALTER TABLE BoardComment ADD (
  parent_id NUMBER NULL,
  root_id   NUMBER NULL,
  depth     NUMBER DEFAULT 0 NOT NULL
);

-- FK: 자기참조(부모 댓글)
ALTER TABLE BoardComment
  ADD CONSTRAINT fk_board_comment_parent
  FOREIGN KEY (parent_id) REFERENCES BoardComment(comment_id);

-- 성능용 인덱스
CREATE INDEX idx_cm_post ON BoardComment(post_id, created_at);
CREATE INDEX idx_cm_parent ON BoardComment(parent_id);
CREATE INDEX idx_cm_root ON BoardComment(root_id);

-- 1) 인덱스 목록
SELECT index_name, uniqueness
FROM user_indexes
WHERE table_name = 'BOARDCOMMENT';

-- 2) 각 인덱스 컬럼
SELECT ic.index_name, ic.column_position, ic.column_name
FROM user_ind_columns ic
WHERE ic.table_name = 'BOARDCOMMENT'
ORDER BY ic.index_name, ic.column_position;

-- 3) 제약조건 (특히 UNIQUE)
SELECT constraint_name, constraint_type, status
FROM user_constraints
WHERE table_name = 'BOARDCOMMENT';

SELECT cc.constraint_name, cc.column_name, cc.position
FROM user_cons_columns cc
WHERE cc.table_name = 'BOARDCOMMENT'
ORDER BY cc.constraint_name, cc.position;

-- (필요시) 부모 FK만 남기고, UNIQUE 류는 제거
-- 예: UNIQUE on (PARENT_ID) 를 발견했다면:
DROP INDEX IDX_BC_PARENT_UNQ; -- 실제 이름으로

-- 예: UNIQUE CONSTRAINT 로 걸려있다면:
ALTER TABLE BoardComment DROP CONSTRAINT UK_BC_PARENT; -- 실제 이름으로

-- 권장 인덱스 (성능용, UNIQUE 아님)
CREATE INDEX IX_BC_POST_PARENT ON BoardComment(post_id, parent_id, created_at);
CREATE INDEX IX_BC_POST_CREATED ON BoardComment(post_id, created_at);

DROP TABLE BoardLike CASCADE CONSTRAINTS;
DROP TABLE BoardAttachment CASCADE CONSTRAINTS;
DROP TABLE BoardComment CASCADE CONSTRAINTS;
DROP TABLE BoardPost CASCADE CONSTRAINTS;
DROP TABLE BoardCategory CASCADE CONSTRAINTS;

SELECT sequence_name 
FROM user_sequences 
WHERE sequence_name LIKE 'SEQ_BOARD_%'
ORDER BY sequence_name;

-- 안전 드롭(있으면 드롭, 없으면 무시)
BEGIN
  FOR r IN (SELECT sequence_name FROM user_sequences WHERE sequence_name LIKE 'SEQ_BOARD_%') LOOP
    EXECUTE IMMEDIATE 'DROP SEQUENCE ' || r.sequence_name;
  END LOOP;
END;
/

SELECT index_name, table_name 
FROM user_indexes 
WHERE table_name LIKE 'BOARD%';

/* =======================================================
   CLEAN START (안전 드롭) 
   - 이미 다 지웠더라도 에러 없이 지나가게 구성
   ======================================================= */
-- 테이블 드롭
BEGIN FOR r IN (
    SELECT table_name FROM user_tables
    WHERE table_name IN ('BOARDLIKE','BOARDATTACHMENT','BOARDCOMMENT','BOARDPOST','BOARDCATEGORY','USER_T')
) LOOP
  EXECUTE IMMEDIATE 'DROP TABLE '||r.table_name||' CASCADE CONSTRAINTS';
END LOOP; END;
/

-- 시퀀스 드롭
BEGIN FOR r IN (
  SELECT sequence_name FROM user_sequences
  WHERE sequence_name IN ('SEQ_BOARD_CATEGORY','SEQ_BOARD_POST','SEQ_BOARD_COMMENT','SEQ_BOARD_ATTACHMENT','SEQ_USER_T')
) LOOP
  EXECUTE IMMEDIATE 'DROP SEQUENCE '||r.sequence_name;
END LOOP; END;
/

-- 트리거 드롭(혹시 남아있다면)
BEGIN
  FOR r IN (
    SELECT trigger_name FROM user_triggers
    WHERE trigger_name IN ('TRG_BOARD_CATEGORY_PK','TRG_BOARD_POST_PK','TRG_BOARD_ATTACHMENT_PK','TRG_BOARD_COMMENT_BIU','TRG_USER_T_PK')
  ) LOOP
    EXECUTE IMMEDIATE 'DROP TRIGGER '||r.trigger_name;
  END LOOP;
END;
/


CREATE SEQUENCE seq_user_t START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_user_t_pk
BEFORE INSERT ON USER_T
FOR EACH ROW
BEGIN
  IF :NEW.user_id IS NULL THEN
    SELECT seq_user_t.NEXTVAL INTO :NEW.user_id FROM dual;
  END IF;
END;
/

/* =======================================================
   2) BoardCategory
   ======================================================= */
CREATE TABLE BoardCategory (
    category_id     NUMBER PRIMARY KEY,
    category_name   VARCHAR2(100) NOT NULL,
    is_active       CHAR(1) DEFAULT 'Y' CHECK (is_active IN ('Y','N')),
    ord             NUMBER
);

CREATE SEQUENCE seq_board_category START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_board_category_pk
BEFORE INSERT ON BoardCategory
FOR EACH ROW
BEGIN
  IF :NEW.category_id IS NULL THEN
    SELECT seq_board_category.NEXTVAL INTO :NEW.category_id FROM dual;
  END IF;
END;
/

/* =======================================================
   3) BoardPost
   ======================================================= */
CREATE TABLE BoardPost (
    post_id     NUMBER PRIMARY KEY,
    category_id NUMBER NOT NULL,
    title       VARCHAR2(200) NOT NULL,
    content     CLOB NOT NULL,
    is_notice   CHAR(1) DEFAULT 'N' CHECK (is_notice IN ('Y','N')),
    view_cnt    NUMBER DEFAULT 0,
    like_cnt    NUMBER DEFAULT 0,
    is_deleted  CHAR(1) DEFAULT 'N' CHECK (is_deleted IN ('Y','N')),
    created_by  NUMBER NOT NULL,
    created_at  TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at  TIMESTAMP,
    CONSTRAINT fk_board_post_category FOREIGN KEY (category_id) REFERENCES BoardCategory(category_id),
    CONSTRAINT fk_board_post_user     FOREIGN KEY (created_by)   REFERENCES USER_T(user_id)
);

CREATE SEQUENCE seq_board_post START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_board_post_pk
BEFORE INSERT ON BoardPost
FOR EACH ROW
BEGIN
  IF :NEW.post_id IS NULL THEN
    SELECT seq_board_post.NEXTVAL INTO :NEW.post_id FROM dual;
  END IF;
END;
/

/* =======================================================
   4) BoardComment  (핵심: 1단계 답글만 허용)
   - parent_id, root_id, depth(0/1), parent_depth(항상 0)
   - (comment_id, depth) UNIQUE + (parent_id, parent_depth) → (comment_id, depth) FK
   ======================================================= */
CREATE TABLE BoardComment (
    comment_id   NUMBER PRIMARY KEY,
    post_id      NUMBER NOT NULL,
    parent_id    NUMBER NULL,
    root_id      NUMBER NULL,
    depth        NUMBER DEFAULT 0 NOT NULL,     -- 0: 댓글, 1: 답글
    parent_depth NUMBER DEFAULT 0 NOT NULL,     -- 부모는 항상 0만 허용(댓글만 부모 가능)
    content      CLOB   NOT NULL,
    is_deleted   CHAR(1) DEFAULT 'N' CHECK (is_deleted IN ('Y','N')),
    created_by   NUMBER NOT NULL,
    created_at   TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at   TIMESTAMP,
    CONSTRAINT fk_board_comment_post  FOREIGN KEY (post_id)    REFERENCES BoardPost(post_id) ON DELETE CASCADE,
    CONSTRAINT fk_board_comment_user  FOREIGN KEY (created_by) REFERENCES USER_T(user_id),
    CONSTRAINT chk_cm_depth           CHECK (depth IN (0,1)),
    CONSTRAINT chk_cm_parent_cons     CHECK (
      (depth = 0 AND parent_id IS NULL) OR
      (depth = 1 AND parent_id IS NOT NULL)
    )
);

-- (comment_id, depth) UNIQUE
ALTER TABLE BoardComment
  ADD CONSTRAINT uq_cm_id_depth UNIQUE (comment_id, depth);

-- (parent_id, parent_depth) → (comment_id, depth)
ALTER TABLE BoardComment
  ADD CONSTRAINT fk_board_comment_parent
  FOREIGN KEY (parent_id, parent_depth)
  REFERENCES BoardComment (comment_id, depth);

-- PK 자동 + depth/root/parent_depth 자동 세팅 (변이테이블 조회 없음)
CREATE SEQUENCE seq_board_comment START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_board_comment_biu
BEFORE INSERT OR UPDATE ON BoardComment
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    IF :NEW.comment_id IS NULL THEN
      SELECT seq_board_comment.NEXTVAL INTO :NEW.comment_id FROM dual;
    END IF;
  END IF;

  IF :NEW.parent_id IS NULL THEN
    :NEW.depth        := 0;
    :NEW.parent_depth := 0;
    :NEW.root_id      := :NEW.comment_id;  -- 루트는 자기 자신
  ELSE
    :NEW.depth        := 1;
    :NEW.parent_depth := 0;                -- 부모는 댓글(depth=0)만 허용
    :NEW.root_id      := :NEW.parent_id;   -- 트리 루트 = 부모 댓글
  END IF;
END;
/

/* =======================================================
   5) BoardAttachment
   ======================================================= */
CREATE TABLE BoardAttachment (
    attach_id    NUMBER PRIMARY KEY,
    post_id      NUMBER NOT NULL,
    orig_name    VARCHAR2(255) NOT NULL,
    save_name    VARCHAR2(255) NOT NULL,
    save_path    VARCHAR2(500) NOT NULL,
    file_size    NUMBER DEFAULT 0,
    content_type VARCHAR2(100),
    uploaded_at  TIMESTAMP DEFAULT SYSTIMESTAMP,
    uploaded_by  NUMBER NOT NULL,
    CONSTRAINT fk_board_attachment_post FOREIGN KEY (post_id)   REFERENCES BoardPost(post_id) ON DELETE CASCADE,
    CONSTRAINT fk_board_attachment_user FOREIGN KEY (uploaded_by) REFERENCES USER_T(user_id)
);

CREATE SEQUENCE seq_board_attachment START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_board_attachment_pk
BEFORE INSERT ON BoardAttachment
FOR EACH ROW
BEGIN
  IF :NEW.attach_id IS NULL THEN
    SELECT seq_board_attachment.NEXTVAL INTO :NEW.attach_id FROM dual;
  END IF;
END;
/

/* =======================================================
   6) BoardLike
   ======================================================= */
CREATE TABLE BoardLike (
    post_id    NUMBER NOT NULL,
    user_id    NUMBER NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT pk_board_like PRIMARY KEY (post_id, user_id),
    CONSTRAINT fk_board_like_post FOREIGN KEY (post_id) REFERENCES BoardPost(post_id) ON DELETE CASCADE,
    CONSTRAINT fk_board_like_user FOREIGN KEY (user_id) REFERENCES USER_T(user_id)
);

/* =======================================================
   7) 인덱스 (성능)
   ======================================================= */
-- 게시글: 카테고리/공지 + 최신순
CREATE INDEX idx_board_post_category ON BoardPost(category_id, created_at DESC);
CREATE INDEX idx_board_post_deleted  ON BoardPost(is_deleted);

-- 댓글: 트리 정렬용
CREATE INDEX idx_cm_post_root_depth_ct ON BoardComment(post_id, root_id, depth, created_at);
CREATE INDEX idx_cm_parent            ON BoardComment(parent_id);
CREATE INDEX idx_cm_post_created      ON BoardComment(post_id, created_at);

-- 첨부
CREATE INDEX idx_board_attachment_post ON BoardAttachment(post_id);

/* =======================================================
   8) 더미 데이터
   ======================================================= */
-- 사용자
INSERT INTO USER_T (login_id, password, name, user_role) VALUES ('admin', 'secret', '관리자', 'ADMIN');
INSERT INTO USER_T (login_id, password, name, user_role) VALUES ('user1', '1111', '홍길동', 'WORKER');
INSERT INTO USER_T (login_id, password, name, user_role) VALUES ('user2', '2222', '김영희', 'WORKER');

-- 카테고리
INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('공지', 'Y', 1);
INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('자유', 'Y', 2);
INSERT INTO BoardCategory (category_name, is_active, ord) VALUES ('질문', 'Y', 3);

-- 게시글 (공지 1 + 일반 2)
INSERT INTO BoardPost (category_id, title, content, is_notice, created_by)
VALUES (1, '시스템 공지', '공지 본문입니다.', 'Y', 1);

INSERT INTO BoardPost (category_id, title, content, created_by)
VALUES (2, '첫 글', '안녕하세요, 첫 글입니다.', 2);

INSERT INTO BoardPost (category_id, title, content, created_by)
VALUES (3, '질문 있어요', '댓글/답글 테스트용 글입니다.', 3);

-- 첨부 (2번 글)
INSERT INTO BoardAttachment (post_id, orig_name, save_name, save_path, file_size, content_type, uploaded_by)
VALUES (2, 'hello.txt', 'a1b2c3.txt', '/upload/2025/09/11', 12, 'text/plain', 2);

-- 좋아요
INSERT INTO BoardLike (post_id, user_id) VALUES (2, 1);
INSERT INTO BoardLike (post_id, user_id) VALUES (2, 2);

-- 댓글/답글 (3번 글에 실습)
-- 댓글 A(루트)
INSERT INTO BoardComment (post_id, content, created_by)
VALUES (3, '댓글 A (depth=0)', 2);

-- 댓글 B(루트)
INSERT INTO BoardComment (post_id, content, created_by)
VALUES (3, '댓글 B (depth=0)', 3);

-- A의 답글들 (여러 개 허용)
-- parent_id = (A의 comment_id). 방금 생성된 ID를 조회해서 넣을 수도 있지만, 
-- 데모에서는 가장 최신 댓글을 가리키도록 간단 삽입 + 업데이트 예시를 보여준다.

-- A의 comment_id 구하기
VAR a_id NUMBER;
BEGIN
  SELECT MAX(comment_id) KEEP (DENSE_RANK FIRST ORDER BY created_at) 
    INTO :a_id
  FROM BoardComment 
  WHERE post_id = 3 AND content LIKE '댓글 A%';
END;
/

-- A에 답글 2개
INSERT INTO BoardComment (post_id, parent_id, content, created_by)
VALUES (3, :a_id, 'A의 답글-1 (depth=1)', 1);
INSERT INTO BoardComment (post_id, parent_id, content, created_by)
VALUES (3, :a_id, 'A의 답글-2 (depth=1)', 3);

-- 대댓글 금지 검증 (의도적 실패 예시는 주석 처리)
-- 아래를 실행하면 fk_board_comment_parent 제약으로 에러가 발생해야 정상
-- INSERT INTO BoardComment (post_id, parent_id, content, created_by)
-- VALUES (3, (SELECT MAX(comment_id) FROM BoardComment WHERE post_id=3 AND content LIKE 'A의 답글-1%'),
--         '답글의 답글(막혀야 함)', 2);

COMMIT;

/* =======================================================
   9) 조회 예시 (필요시 테스트)
   ======================================================= */
-- 게시글 목록(공지 우선 + 최신순) 페이징 샘플
-- 바인드 없이 러프하게 확인용
SELECT * FROM (
  SELECT p.*,
         ROW_NUMBER() OVER (
           ORDER BY CASE WHEN p.is_notice='Y' THEN 0 ELSE 1 END, p.created_at DESC
         ) rn
  FROM BoardPost p
  WHERE p.is_deleted='N'
) t
WHERE t.rn BETWEEN 1 AND 50;

-- 글 상세 + 작성자/카테고리
SELECT p.*, u.name AS writer_name, c.category_name
FROM BoardPost p
JOIN USER_T u ON u.user_id = p.created_by
JOIN BoardCategory c ON c.category_id = p.category_id
WHERE p.post_id = 3;

-- 댓글 트리(0/1단계): 댓글 → 답글 순으로
SELECT cm.*, u.name AS writer_name
FROM BoardComment cm
JOIN USER_T u ON u.user_id = cm.created_by
WHERE cm.post_id = 3 AND cm.is_deleted = 'N'
ORDER BY cm.root_id, cm.depth, cm.created_at;

select * from code_detail;
select * from inventory_transaction;
COMMIT;
