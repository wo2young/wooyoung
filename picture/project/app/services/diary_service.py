from app.database import POOL


# -------------------------------
# 일기 생성
# -------------------------------
def create_diary(data):
    sql = """
        INSERT INTO diary (
            family_id, writer_id, title, content, diary_date
        )
        VALUES (%(family_id)s, %(writer_id)s, %(title)s, %(content)s, %(diary_date)s)
        RETURNING id, created_at;
    """

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, data)
            row = cur.fetchone()
            return {"id": row["id"], "created_at": row["created_at"]}


# -------------------------------
# 일기 상세 조회
# -------------------------------
def get_diary(diary_id: int):
    sql = "SELECT * FROM diary WHERE id = %(id)s AND deleted_at IS NULL;"

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"id": diary_id})
            return cur.fetchone()


# -------------------------------
# 가족별 일기 목록 조회
# -------------------------------
def list_diaries_by_family(family_id: int):
    sql = """
        SELECT *
        FROM diary
        WHERE family_id = %(family_id)s
          AND deleted_at IS NULL
        ORDER BY diary_date DESC, created_at DESC;
    """

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"family_id": family_id})
            return cur.fetchall()


# -------------------------------
# 일기 수정
# -------------------------------
def update_diary(diary_id: int, data):
    sql = """
        UPDATE diary
        SET
            title = COALESCE(%(title)s, title),
            content = COALESCE(%(content)s, content),
            diary_date = COALESCE(%(diary_date)s, diary_date),
            updated_at = NOW()
        WHERE id = %(id)s AND deleted_at IS NULL
        RETURNING id;
    """

    params = dict(data)
    params["id"] = diary_id

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, params)
            row = cur.fetchone()
            return {"updated": row is not None}


# -------------------------------
# 일기 삭제 (soft delete)
# -------------------------------
def delete_diary(diary_id: int):
    sql = """
        UPDATE diary
        SET deleted_at = NOW()
        WHERE id = %(id)s;
    """

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"id": diary_id})
            return {"deleted": True}
