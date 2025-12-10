from typing import Any, Dict, List, Optional

from sqlalchemy import text
from sqlalchemy.engine import Result

from app.database import async_session_maker


# -------------------------------
# 일기 생성 (비동기)
# -------------------------------
async def create_diary(data: Dict[str, Any]) -> Dict[str, Any]:
    """
    diary INSERT 후 id, created_at 반환
    data 예:
    {
        "family_id": 1,
        "writer_id": 2,
        "title": "제목",
        "content": "내용",
        "diary_date": "2025-12-10"
    }
    """
    sql = text(
        """
        INSERT INTO diary (
            family_id, writer_id, title, content, diary_date
        )
        VALUES (:family_id, :writer_id, :title, :content, :diary_date)
        RETURNING id, created_at;
        """
    )

    async with async_session_maker() as session:
        result: Result = await session.execute(sql, data)
        row = result.mappings().first()
        await session.commit()

        if row is None:
            raise RuntimeError("일기 생성에 실패했습니다.")

        return {
            "id": row["id"],
            "created_at": row["created_at"],
        }


# -------------------------------
# 일기 상세 조회 (비동기)
# -------------------------------
async def get_diary(diary_id: int) -> Optional[Dict[str, Any]]:
    sql = text(
        """
        SELECT *
        FROM diary
        WHERE id = :id
          AND deleted_at IS NULL;
        """
    )

    async with async_session_maker() as session:
        result: Result = await session.execute(sql, {"id": diary_id})
        row = result.mappings().first()

        if row is None:
            return None

        return dict(row)


# -------------------------------
# 가족별 일기 목록 조회 (비동기)
# -------------------------------
async def list_diaries_by_family(family_id: int) -> List[Dict[str, Any]]:
    sql = text(
        """
        SELECT *
        FROM diary
        WHERE family_id = :family_id
          AND deleted_at IS NULL
        ORDER BY diary_date DESC, created_at DESC;
        """
    )

    async with async_session_maker() as session:
        result: Result = await session.execute(sql, {"family_id": family_id})
        rows = result.mappings().all()
        return [dict(r) for r in rows]


# -------------------------------
# 일기 수정 (비동기)
# -------------------------------
async def update_diary(diary_id: int, data: Dict[str, Any]) -> Dict[str, bool]:
    """
    부분 수정 가능.
    data 안에 없는 컬럼은 기존 값 유지 (COALESCE 사용)
    """
    sql = text(
        """
        UPDATE diary
        SET
            title      = COALESCE(:title, title),
            content    = COALESCE(:content, content),
            diary_date = COALESCE(:diary_date, diary_date),
            updated_at = NOW()
        WHERE id = :id
          AND deleted_at IS NULL
        RETURNING id;
        """
    )

    params = dict(data)
    params["id"] = diary_id

    async with async_session_maker() as session:
        result: Result = await session.execute(sql, params)
        row = result.mappings().first()
        await session.commit()

        return {"updated": row is not None}


# -------------------------------
# 일기 삭제 (soft delete, 비동기)
# -------------------------------
async def delete_diary(diary_id: int) -> Dict[str, bool]:
    sql = text(
        """
        UPDATE diary
        SET deleted_at = NOW()
        WHERE id = :id;
        """
    )

    async with async_session_maker() as session:
        result = await session.execute(sql, {"id": diary_id})
        await session.commit()

        # rowcount 로 실제로 몇 개 행이 바뀌었는지 확인
        deleted = result.rowcount > 0

        return {"deleted": deleted}
