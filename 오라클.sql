
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
                            --3일차
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

select *
from emp
where comm is null and
mgr is not null 
and job = in ('MANAGER', 'CLERK')
and ename not like '_L%';

-- 함수  사용
select ENAME, upper(ename), lower(ename), initcap(ename)
from emp;

select ename
from emp
where lower(ename) like lower('%aM%');

select upper('abc') from dual;

select ename
from emp
where length(ename) = 5;

select ename, length(ename)
from emp
where length(ename) >= 5;

                     -- 4일차
select job, substr(job, 3, 2), substr(job, 5)
from emp;

select *
from emp
where ename like '%S%';

select *
from emp
where deptno = 10
order by empno desc
union all
select *
from emp
where deptno = 20
order by empno;

select substr(ename, 2, 3)
from emp;

select job, substr(job, -3, 2)
from emp;

select ename, substr(ename, -3, 3)
from emp;

-- L이 들어가는 사람들만 찾기
select ename, substr(ename,instr(ename, 'L'))
from emp;

select ename, substr(ename,instr(ename, 'L')) 
from emp
where ename like '%L%';

select replace('a-b-c', '-', '*'),
       replace('a-b-c', '-'),
       replace('a-b-c', '-', '')
from emp;

select replace(ename,'E','-'),
       replace(ename,'A','*')
from emp;

select ename, lpad(ename, 10, '?')
from emp;

-- 이름을 마스킹하는 버전 1 앞에 두글자만 원본을 출력하고 나머자는 4개의 *로 표시
select ename, rpad(substr(ename, 1, 2), 6, '*')
from emp;

--사원 이름 두글자만 보이고 나머지는 *로. 단, 원래 이름 길이 만큼 표시
select ename, rpad(substr(ename, 1, 2), length(ename), '*')
from emp;

--이름을 총 20자 중 가운데 정렬             심화 CUT
select ename, rpad(lpad(ename, length(ename)+(20-length(ename))/2,' '), 20,' ') 
as ena_me from emp; /* 글자수 + (전체글자수 - 글자수)/2가 가운제 정렬 공식 */
/* 전체글자수에서 글자수를 빼면 공백의 값이 나온다 여기에 /2를 해야 앞뒤에 나누어 가운데 
정렬이 가능해진다 */
--job을 기준으로 20자 가운데 정렬
select job, rpad(lpad(job, length(job)+(20-length(job))/2,' '), 20,' ') as jo_b
from emp;

--중요한 문자 역슬래시 + 쉬프트 = |(concat 대신에 문자 연결할 때 많이 사용)
select empno || ename, empno || ' : ' || ename
from emp;

select '   ab c  ', trim('   ab c  ') from dual;

select round(1234.5678) as round,
       round(1234.5678, 0) as round_0,
       round(1234.5678, 1) as round_1,
       round(1234.5678, 2) as round_2,
       round(1234.5678, -1) as round_minus1,
       round(1234.5678, -2) as round_minus2
  from dual;
  
select trunc(1234.5678) as trunc,
       trunc(1234.5678, 1) as trunc_1,
       trunc(1234.5678, 2) as trunc_2,
       trunc(1234.5678, -1) as trunc_minus1,
       trunc(1234.5678, -2) as trunc_minus2
    from dual;
    
select ceil(3.14),
       floor(3.14),
       ceil(-3.14),
       floor(-3.14)
    from dual;

select mod(15, 6),
       mod(10, 2),
       mod(11, 2)
    from dual;

-- 출력시 날짜만 나오지만 시,분,초 모두 포함되어있음.
select sysdate,
       sysdate-1 as yesterday,
       sysdate+1 as tomorrow
from dual;

select empno, empno + 1000
from emp;

select 'a' ||' : ' || 'b'
from dual;
--- to_char 문자로 바꾼다, to_number 숫자로 바꾼다. to_date 날짜로 바꾼다.
select to_char(sysdate, 'yyyy/mm/dd/hh24:mi:ss') as  지금시간
from dual;

select to_char(hiredate, 'yyyy/mm/dd/hh24:mi:ss') as  입사년도
from emp;

select to_number('1,300', '999,999') - to_number('1,500', '999,999')
from dual;

select to_date('2024-08-14', 'yyyy-mm-dd') as todate1,
       to_date('2024/08/14', 'yyyy/mm/dd') as todate2
    from dual;
    
select * 
from emp
where hiredate > to_date('1981/06/01', 'yyyy/mm/dd');

-- nvl 오라클에서만 사용가능 많이 사용
select empno, ename, sal, comm, sal + comm,
       nvl(comm, 0), /* 원래 null이 나오던걸 계산할 수 있어진다. */
       sal + nvl(comm, 0)
   from emp;

