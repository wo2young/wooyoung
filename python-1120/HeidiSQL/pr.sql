CREATE DATABASE study_db;
-- 테이블 만들기
CREATE TABLE tb_test (
	NAME VARCHAR,
	AGE INT,
	birth TIMESTAMP
);

/*
조회
select 컬럼명, 컬럼명
from 테이블명
*/
SELECT NAME, AGE, birth FROM tb_test;
SELECT * FROM tb_test;
SELECT NAME FROM tb_test;

INSERT INTO tb_test(NAME, AGE, birth)
VALUES 
('이름1', 20, '2025-12-01'),
('이름2', 21, '2025-12-02')

COMMIT;

SELECT * FROM tb_test
WHERE NAME = '이름2';

SELECT * FROM tb_test
WHERE AGE = 20;

/*
수정
update 테이블명
set 컬럼명 = 값, 컬러명 = 값
where 조건 꼭 쓰기
*/
UPDATE tb_test
SET AGE = 30
WHERE NAME = '이름2';

/*
삭제
delete from 테이블명
where 조건 꼭 쓰기
*/
DELETE from tb_test
WHERE AGE = 30;

SELECT NOW()

SELECT * FROM emp;

COMMIT;