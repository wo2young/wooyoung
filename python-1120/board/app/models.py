# app/models.py
from psycopg_pool import ConnectionPool
from psycopg.rows import dict_row

# ====== DB Connection Pool ======
pool = ConnectionPool(
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

# ====== SELECT ALL ======
def selectAll():
    with pool.connection() as conn:
        with conn.cursor() as cursor:
            sql = '''
                SELECT 
                    b.board_id,
                    b.title,
                    b.user_id,
                    u.username,
                    b.views,
                    b.created_at,
                    b.updated_at
                FROM board b
                LEFT JOIN board_user u
                    ON b.user_id = u.user_id
                ORDER BY b.board_id DESC
            '''
            cursor.execute(sql)
            return cursor.fetchall()


# ====== SELECT ONE ======
def selectOne(board_id):
    with pool.connection() as conn:
        with conn.cursor() as cursor:
            sql = '''
                SELECT * FROM board
                WHERE board_id = %s
            '''
            cursor.execute(sql, (board_id,))
            return cursor.fetchone()
        
def selectUser(user_id) :
     with pool.connection() as conn:
        with conn.cursor() as cursor:
            sql = '''
                SELECT * FROM board_user
                WHERE user_id = %s
            '''
            cursor.execute(sql, (user_id,))
            return cursor.fetchone()

# ===== 로그인용 함수 =====
def loginUser(user_id, user_pw):
    with pool.connection() as conn:
        with conn.cursor() as cursor:
            sql = '''
                SELECT * FROM board_user
                WHERE user_id = %s AND user_pw = %s
            '''
            cursor.execute(sql, (user_id, user_pw))
            return cursor.fetchone()

# ====== INSERT ======
def insertBoard(info):
    try:
        with pool.connection() as conn:
            with conn.cursor() as cursor:
                sql = '''
                    INSERT INTO board(board_id, title, content, user_id, created_at)
                    VALUES(nextval('seq_board_id'), %s, %s, %s, NOW())
                    RETURNING board_id;
                '''
                cursor.execute(sql, (info['title'], info['content'], info['user_id']))
                new_id = cursor.fetchone()['board_id']
            conn.commit()
            return new_id
    except Exception as e:
        print("에러발생:", e)

def insertUser(user_info):
    try:
        with pool.connection() as conn:
            with conn.cursor() as cursor:
                sql = '''
                    INSERT INTO board_user(user_id, user_pw, username, created_at)
                    VALUES(%s, %s, %s, NOW());
                '''
                cursor.execute(sql, (user_info['user_id'], user_info['user_pw'], user_info['username']))
            conn.commit()
            return new_id
    except Exception as e:
        print("에러발생 롤백 자동 : ", e)
        return None


# ====== UPDATE ======
def updateBoard(info):
    try:
        with pool.connection() as conn:
            with conn.cursor() as cursor:
                sql = '''
                    UPDATE board
                    SET title = %s,
                        content = %s,
                        updated_at = NOW()
                    WHERE board_id = %s
                '''
                cursor.execute(sql, (info['title'], info['content'], info['board_id']))
            conn.commit()
    except Exception as e:
        print("UPDATE 오류:", e)

def updateUser(user_info):
    try:
        with pool.connection() as conn:
            with conn.cursor() as cursor:
                sql = '''
                    UPDATE board_user
                    SET username = %s,
                        user_pw = %s,
                        updated_at = NOW()
                    WHERE user_id = %s
                '''
                cursor.execute(sql, (
                    user_info['username'],   # 1번
                    user_info['user_pw'],    # 2번
                    user_info['user_id']     # 3번
                ))
            conn.commit()
    except Exception as e:
        print("UPDATE 오류:", e)


# ====== DELETE ======
def deleteBoard(board_id):
    try:
        with pool.connection() as conn:
            with conn.cursor() as cursor:
                sql = '''
                    DELETE FROM board
                    WHERE board_id = %s
                '''
                cursor.execute(sql, (board_id,))
            conn.commit()
    except Exception as e:
        print("DELETE 오류:", e)
        
def deleteUser(user_id):
    try:
        with pool.connection() as conn:
            with conn.cursor() as cursor:
                sql = '''
                    DELETE FROM board_user
                    WHERE user_id = %s
                '''
                cursor.execute(sql, (user_id,))
            conn.commit()
    except Exception as e:
        print("DELETE 오류:", e)
