# app/services/photo_service.py

from sqlalchemy import text
from sqlalchemy.ext.asyncio import AsyncSession
from app.database import async_session_maker


# ----------------------------------
# 사진 생성
# ----------------------------------
async def create_photo(data: dict):
    sql = text("""
        INSERT INTO photo (
            album_id, uploader_id, original_url, thumbnail_url,
            description, place, taken_at
        )
        VALUES (
            :album_id, :uploader_id, :original_url, :thumbnail_url,
            :description, :place, :taken_at
        )
        RETURNING id, created_at;
    """)

    async with async_session_maker() as session:     # DB 접속
        result = await session.execute(sql, data)    # 실행
        row = result.fetchone()
        await session.commit()

        return {
            "id": row.id,
            "created_at": row.created_at
        }


# ----------------------------------
# 사진 상세 조회
# ----------------------------------
async def get_photo(photo_id: int):
    sql = text("""
        SELECT * FROM photo
        WHERE id = :id AND deleted_at IS NULL;
    """)

    async with async_session_maker() as session:
        result = await session.execute(sql, {"id": photo_id})
        return result.fetchone()


# ----------------------------------
# 앨범별 사진 목록 조회
# ----------------------------------
async def list_photos_by_album(album_id: int):
    sql = text("""
        SELECT *
        FROM photo
        WHERE album_id = :album_id
          AND deleted_at IS NULL
        ORDER BY created_at DESC;
    """)

    async with async_session_maker() as session:
        result = await session.execute(sql, {"album_id": album_id})
        return result.fetchall()


# ----------------------------------
# 사진 삭제 (soft delete)
# ----------------------------------
async def delete_photo(photo_id: int):
    sql = text("""
        UPDATE photo
        SET deleted_at = NOW()
        WHERE id = :photo_id;
    """)

    async with async_session_maker() as session:
        await session.execute(sql, {"photo_id": photo_id})
        await session.commit()
        return {"deleted": True}
