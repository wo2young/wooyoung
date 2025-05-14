-- 한줄 주석              주석 자주 사용
/* 범위 주석 */
/* 여러줄
   주석
*/            --1일차

-- select문 조회      기본 중요
select * from emp;
select * from dept;
select * from salgrade;

select EMPNO, ENAME, DEPTNO
      FROM EMP;

-- 이름과 급여만 출력
select ename, sal
from emp;

--distinct문 중복데이터 삭제  별로 안중요
select deptmo
from emp;

select distinct deptno
from emp;

select distinct job, deptno
from emp;

--all은 생략가능
select all job, deptno
from emp;

--별칭 as는 생략가능             나름 사용
select ename, sal, sal*12+comm, comm
from emp;

select ename, sal, sal*12+comm s, comm
from emp;

--"" 사용시 뛰어쓰기 가능 ""는 별칭에만 사용
select ename, sal, sal*12+comm as "s  s", comm 추가수당
from emp;
            --2일차
--order by절(정렬) 생략시 asc(오름차순) / desc(내림차순)   자주사용
select *
from emp
order by sal;

select *
from emp
order by sal desc;

select *
from emp
order by deptno asc, sal desc;

select *
from emp
order by ename desc;

select *
from emp
order by deptno, sal desc, empno desc;

--되새김 문제
select distinct job
from emp;

select empno employee_no, ename employee_name, mgr manager,
sal salary, comm commisson, deptno department_no  
from emp
order by  department_no desc, employee_no asc;

--where절(조건)
select *
from emp
WHERE deptno = 30;

--where 조건에 and, or 사용
select *
from emp
where deptno = 30
and job = 'SALESMAN';

select *
from emp
where deptno = 30
or job = 'CLERK';

--연산자 종류 산술, 비교, 논리 부정, in, between a and b, like, is null, 집합 등
select *
from emp
where sal*12 = 36000;

select *
from emp
where sal >= 3000;

select *
from emp
where ename >= 'F';

select *
from emp
where ename <= 'FORZ';
-- != 과 <>, ^= 은 같은 결과값을 낸다.
select *
from emp
where sal != 3000;

select *
from emp
where sal <> 3000;

select *
from emp
where sal ^= 3000;

select *
from emp
where not sal = 3000;



---1번
select *
from emp
where deptno = 10;
---2번
select *
from emp
where not deptno = 10;
---3번
select *
from emp
where sal >= 3000;
---4번
select *
from emp
where sal >= 1500 and sal <= 3000;
---5번
select *
from emp
where not (sal >= 1500 and sal <= 3000);
---6번
select *
from emp
where not (sal >= 1500 and sal <= 3000)
order by sal, ename desc;
---7번
select ename, sal
from emp
where deptno in(20, 30) and sal >=1500
order by sal;
