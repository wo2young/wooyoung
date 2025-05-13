-- 한줄 주석
/* 범위 주석 */
/* 여러줄
   주석
*/

-- select문 조회
select * from emp;
select * from dept;
select * from salgrade;

select EMPNO, ENAME, DEPTNO
      FROM EMP;

-- 이름과 급여만 출력
select ename, sal
from emp;

--distinct문 중복데이터 삭제
select deptmo
from emp;

select distinct deptno
from emp;

select distinct job, deptno
from emp;

--all은 생략가능
select all job, deptno
from emp;

--별칭 as는 생략가능
select ename, sal, sal*12+comm, comm
from emp;

select ename, sal, sal*12+comm s, comm
from emp;

--"" 사용시 뛰어쓰기 가능 ""는 별칭에만 사용
select ename, sal, sal*12+comm as "s  s", comm 추가수당
from emp;

