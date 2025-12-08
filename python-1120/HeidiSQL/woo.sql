-- ================================
-- board_user
-- ================================
CREATE TABLE board_user (
    user_id      VARCHAR PRIMARY KEY,
    user_pw      VARCHAR NOT NULL,
    username     VARCHAR NOT NULL,
    created_at   TIMESTAMP
);

INSERT INTO board_user (user_id, user_pw, username, created_at)
VALUES
    ('user01', '1234', '홍길동', NOW()),
    ('user02', '1234', '김철수', NOW()),
    ('user03', '1234', '이영희', NOW());

SELECT * from board_user;
-- ================================
-- board
-- ================================
CREATE TABLE board (
    board_id    INT PRIMARY KEY,
    title       VARCHAR NOT NULL,
    user_id     VARCHAR,
    views       INT,
    likes        INT,
    created_at  TIMESTAMP,
    content     VARCHAR,
    updated_at  TIMESTAMP,
    status      VARCHAR,
    CONSTRAINT fk_board_user
        FOREIGN KEY (user_id)
        REFERENCES board_user (user_id)
);

CREATE SEQUENCE seq_board_id START 1 INCREMENT 1;
SELECT setval('seq_board_id', (SELECT MAX(board_id) FROM board));

INSERT INTO board (board_id, title, user_id, views, likes, created_at, content, updated_at, status)
VALUES
    (1, '첫 번째 글입니다', 'user01', 10, 1, NOW(), '내용 테스트 1', NOW(), 'ACTIVE'),
    (2, '두 번째 글 테스트', 'user02', 5, 0, NOW(), '내용 테스트 2', NOW(), 'ACTIVE'),
    (3, '세 번째 글', 'user03', 17, 2, NOW(), '내용 테스트 3', NOW(), 'ACTIVE');
    
SELECT * FROM board;

-- ================================
-- board_like
-- ================================
CREATE TABLE board_like (
    like_id     INT PRIMARY KEY,
    board_id    INT,
    user_id     VARCHAR,
    status      VARCHAR,
    created_at  TIMESTAMP,
    
    -- UNQIUE: 한 유저가 한 게시글에 좋아요 1번만 가능
    CONSTRAINT uq_board_like UNIQUE (board_id, user_id),

    -- FK: 게시글
    CONSTRAINT fk_boardlike_board
        FOREIGN KEY (board_id)
        REFERENCES board (board_id),

    -- FK: 유저
    CONSTRAINT fk_boardlike_user
        FOREIGN KEY (user_id)
        REFERENCES board_user (user_id)
);

INSERT INTO board_like (like_id, board_id, user_id, status, created_at)
VALUES
    (1, 1, 'user02', 'LIKE', NOW()),
    (2, 1, 'user03', 'LIKE', NOW()),
    (3, 3, 'user01', 'LIKE', NOW());

SELECT * from board_like


-- ================================
-- board_comment
-- ================================
CREATE TABLE board_coment (
    coment_id   INT PRIMARY KEY,
    board_id    INT,
    content     VARCHAR,
    parent_id   INT,
    user_id     VARCHAR,
    status      VARCHAR,
    created_at  TIMESTAMP,

    -- FK: 게시글
    CONSTRAINT fk_comment_board
        FOREIGN KEY (board_id)
        REFERENCES board (board_id),

    -- FK: 작성자
    CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id)
        REFERENCES board_user (user_id),

    -- FK: 부모 댓글 (대댓글)
    CONSTRAINT fk_comment_parent
        FOREIGN KEY (parent_id)
        REFERENCES board_coment (coment_id)
        ON DELETE CASCADE
);

INSERT INTO board_coment (coment_id, board_id, content, parent_id, user_id, status, created_at)
VALUES
    -- 게시글 1의 댓글
    (1, 1, '좋은 글이네요!', NULL, 'user02', 'ACTIVE', NOW()),
    (2, 1, '저도 동의합니다.', NULL, 'user03', 'ACTIVE', NOW()),

    -- 댓글 1에 대한 대댓글
    (3, 1, '대댓글 테스트입니다', 1, 'user01', 'ACTIVE', NOW()),

    -- 게시글 2의 댓글
    (4, 2, '두 번째 글 댓글입니다', NULL, 'user01', 'ACTIVE', NOW());

SELECT * FROM board_coment;