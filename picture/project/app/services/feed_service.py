from app.database import POOL


# ---------------------------------------------------------
# 공통: user_id 기준으로 접근 가능한 family_id 조회
# ---------------------------------------------------------
FAMILY_FILTER_SQL = """
    WITH related AS (
        SELECT id FROM app_user WHERE id = %(user_id)s
        UNION
        SELECT father_id FROM app_user WHERE id = %(user_id)s AND father_id IS NOT NULL
        UNION
        SELECT mother_id FROM app_user WHERE id = %(user_id)s AND mother_id IS NOT NULL
        UNION
        SELECT spouse_id FROM app_user WHERE id = %(user_id)s AND spouse_id IS NOT NULL
    ),
    families AS (
        SELECT DISTINCT family_id
        FROM family_member
        WHERE user_id IN (SELECT id FROM related)
    )
    SELECT family_id FROM families;
"""


# ---------------------------------------------------------
# 1) 최신순 Feed (사진 + 일기)
# ---------------------------------------------------------
def get_feed_latest(user_id: int):
    sql = """
        WITH related AS (
            SELECT id FROM app_user WHERE id = %(user_id)s
            UNION
            SELECT father_id FROM app_user WHERE id = %(user_id)s AND father_id IS NOT NULL
            UNION
            SELECT mother_id FROM app_user WHERE id = %(user_id)s AND mother_id IS NOT NULL
            UNION
            SELECT spouse_id FROM app_user WHERE id = %(user_id)s AND spouse_id IS NOT NULL
        ),
        families AS (
            SELECT DISTINCT family_id
            FROM family_member
            WHERE user_id IN (SELECT id FROM related)
        ),
        album_ids AS (
            SELECT a.id
            FROM album a
            JOIN album_folder f ON a.folder_id = f.id
            WHERE f.family_id IN (SELECT family_id FROM families)
        ),
        photos AS (
            SELECT 
                'photo' AS type,
                p.id AS content_id,
                p.created_at AS sort_date,
                p.original_url AS value
            FROM photo p
            WHERE p.album_id IN (SELECT id FROM album_ids)
        ),
        diaries AS (
            SELECT
                'diary' AS type,
                d.id AS content_id,
                d.created_at AS sort_date,
                d.title AS value
            FROM diary d
            WHERE d.family_id IN (SELECT family_id FROM families)
        )
        SELECT * 
        FROM (
            SELECT * FROM photos
            UNION ALL
            SELECT * FROM diaries
        ) x
        ORDER BY sort_date DESC
        LIMIT 50;
    """

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"user_id": user_id})
            return cur.fetchall()



# ---------------------------------------------------------
# 2) 랜덤 피드 (사진만)
# ---------------------------------------------------------
def get_feed_random(user_id: int):
    sql = """
        WITH related AS (
            SELECT id FROM app_user WHERE id = %(user_id)s
            UNION
            SELECT father_id FROM app_user WHERE id = %(user_id)s AND father_id IS NOT NULL
            UNION
            SELECT mother_id FROM app_user WHERE id = %(user_id)s AND mother_id IS NOT NULL
            UNION
            SELECT spouse_id FROM app_user WHERE id = %(user_id)s AND spouse_id IS NOT NULL
        ),
        families AS (
            SELECT DISTINCT family_id
            FROM family_member
            WHERE user_id IN (SELECT id FROM related)
        ),
        album_ids AS (
            SELECT a.id
            FROM album a
            JOIN album_folder f ON a.folder_id = f.id
            WHERE f.family_id IN (SELECT family_id FROM families)
        )
        SELECT *
        FROM photo
        WHERE album_id IN (SELECT id FROM album_ids)
        ORDER BY RANDOM()
        LIMIT 20;
    """

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"user_id": user_id})
            return cur.fetchall()



# ---------------------------------------------------------
# 3) 통합 Feed (type+value만 단순하게)
# ---------------------------------------------------------
def get_feed_all(user_id: int):
    sql = """
        WITH related AS (
            SELECT id FROM app_user WHERE id = %(user_id)s
            UNION
            SELECT father_id FROM app_user WHERE id = %(user_id)s AND father_id IS NOT NULL
            UNION
            SELECT mother_id FROM app_user WHERE id = %(user_id)s AND mother_id IS NOT NULL
            UNION
            SELECT spouse_id FROM app_user WHERE id = %(user_id)s AND spouse_id IS NOT NULL
        ),
        families AS (
            SELECT DISTINCT family_id
            FROM family_member
            WHERE user_id IN (SELECT id FROM related)
        ),
        album_ids AS (
            SELECT a.id
            FROM album a
            JOIN album_folder f ON a.folder_id = f.id
            WHERE f.family_id IN (SELECT family_id FROM families)
        ),
        photos AS (
            SELECT 
                'photo' AS type,
                p.id AS content_id,
                p.created_at AS sort_date,
                p.original_url AS value
            FROM photo p
            WHERE p.album_id IN (SELECT id FROM album_ids)
        ),
        diaries AS (
            SELECT
                'diary' AS type,
                d.id AS content_id,
                d.created_at AS sort_date,
                d.title AS value
            FROM diary d
            WHERE d.family_id IN (SELECT family_id FROM families)
        )
        SELECT *
        FROM (
            SELECT * FROM photos
            UNION ALL
            SELECT * FROM diaries
        ) x;
    """

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"user_id": user_id})
            return cur.fetchall()
