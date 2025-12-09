from app.database import POOL


def create_photo(data):
    sql = """
        INSERT INTO photo (
            album_id, uploader_id, original_url, thumbnail_url,
            description, place, taken_at
        )
        VALUES (%(album_id)s, %(uploader_id)s, %(original_url)s, %(thumbnail_url)s,
                %(description)s, %(place)s, %(taken_at)s)
        RETURNING id, created_at;
    """

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, data)
            row = cur.fetchone()
            return {"id": row["id"], "created_at": row["created_at"]}


def get_photo(photo_id: int):
    sql = "SELECT * FROM photo WHERE id = %(id)s AND deleted_at IS NULL;"
    
    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"id": photo_id})
            return cur.fetchone()


def list_photos_by_album(album_id: int):
    sql = """
        SELECT *
        FROM photo
        WHERE album_id = %(album_id)s
          AND deleted_at IS NULL
        ORDER BY created_at DESC;
    """
    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"album_id": album_id})
            return cur.fetchall()


def delete_photo(photo_id: int):
    sql = """
        UPDATE photo
        SET deleted_at = NOW()
        WHERE id = %(photo_id)s;
    """

    with POOL.connection() as conn:
        with conn.cursor() as cur:
            cur.execute(sql, {"photo_id": photo_id})
            return {"deleted": True}
