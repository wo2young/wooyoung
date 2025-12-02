# import psycopg
from psycopg_pool import ConnectionPool
from psycopg.rows import dict_row

# def get_conn() :
#     return psycopg.connect(
#         host = "127.0.0.1",
#         port = "5432",
#         dbname = "study_db",
#         user = "postgres",
#         password = "rladndud",
#         row_factory=dict_row
#     )
POOL = ConnectionPool(
    conninfo='''
        host=127.0.0.1
        port=5432
        dbname=study_db
        user=postgres
        password=rladndud
    ''',
    min_size=1,
    max_size=10,
    kwargs={"row_factory": dict_row}
)

def get_conn() :
    return POOL.connection()

def selectAll() :
    sql = 'select * from emp'
    
    conn = get_conn()
    cursor = conn.cursor()
    
    cursor.execute(sql)
    rows = cursor.fetchall()
    
    # print(type(rows), rows)
    # print(rows[0]['empno'], rows[0]['ename'], rows[0]['sal'])
    
    cursor.close()
    conn.close()
    

def selectOne(empno) :
    # with : close 자동
    with get_conn() as conn :
        with conn.cursor() as cursor :
            sql = 'select * from emp where empno >= %s'
            cursor.execute(sql, (empno,))
            row = cursor.fetchone() # 여러줄이여도 첫번째 줄만 나옴
            print(row)

# 월급(이상), 부서번호 모두 조회
def selectEmp( sal, deptno) :
    # 이름, 월급, 부서번호 출력
    with get_conn() as conn :
        with conn.cursor() as cursor :
            sql = 'select ename, sal, deptno from emp where sal >= %s and deptno =%s'
            cursor.execute(sql, (sal, deptno))
            rows = cursor.fetchall()
            for i in range(0, len(rows)) :
                print(rows[i])

def insertEmp(info) :
    try :
        with get_conn() as conn :
            with conn.cursor() as cursor :
                sql = '''
                    insert into emp(empno, ename, sal, deptno)
                    values(%s,%s,%s,%s)
                '''
                cre = cursor.execute(sql, (info['empno'],info['ename'],info['sal'],info['deptno']))
                print(cre)
            conn.commit()
    except Exception as e :
        print("에러발생 롤백 자동 : ", e)

def updateEmp() :
    sql = '''
        update emp
        set sal = 5000
        where empno = 9999 
    '''
    try :
        with get_conn() as conn :
            with conn.cursor() as cursor :
                cursor.execute(sql)
                print('result', cursor.rowcount) # 영향 받은 row 수
            conn.commit()
    except Exception as e :
        print("에러발생 롤백 자동 : ", e)
 
def deleteEmp() :
    sql = '''
        delete from emp
        where empno = 9999 
    '''
    try :
        with get_conn() as conn :
            with conn.cursor() as cursor :
                cursor.execute(sql)
                print('cursor.rowcount', cursor.rowcount) # 영향 받은 row 수
            conn.commit()
    except Exception as e :
        print("에러발생 롤백 자동 : ", e)   
        
    
# selectAll()
# selectOne(7369)
# selectEmp(2000, 20)
# info = {
#     'empno' : 9999,
#     'ename' : '우영',
#     'sal' : 3500,
#     'deptno' : 10
# }

# insertEmp(info)
# updateEmp()
deleteEmp()
POOL.close()