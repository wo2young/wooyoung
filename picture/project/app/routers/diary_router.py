from fastapi import APIRouter, HTTPException
from app.schemas.diary_schema import DiaryCreate, DiaryUpdate
from app.services.diary_service import (
    create_diary,
    get_diary,
    list_diaries_by_family,
    update_diary,
    delete_diary
)

router = APIRouter(prefix="/diaries", tags=["Diary"])


# ----------------------
# 일기 생성
# ----------------------
@router.post("/", summary="일기 생성")
async def create_diary_api(payload: DiaryCreate):
    result = await create_diary(payload.dict())
    return result


# ----------------------
# 일기 상세 조회
# ----------------------
@router.get("/{diary_id}", summary="일기 상세 조회")
async def get_diary_api(diary_id: int):
    diary = await get_diary(diary_id)
    if diary is None:
        raise HTTPException(status_code=404, detail="Diary not found")
    return diary


# ----------------------
# 가족별 일기 목록
# ----------------------
@router.get("/family/{family_id}", summary="가족별 일기 목록 조회")
async def list_diaries_api(family_id: int):
    diaries = await list_diaries_by_family(family_id)
    return diaries


# ----------------------
# 일기 수정
# ----------------------
@router.put("/{diary_id}", summary="일기 수정")
async def update_diary_api(diary_id: int, payload: DiaryUpdate):
    result = await update_diary(diary_id, payload.dict())
    return result


# ----------------------
# 일기 삭제
# ----------------------
@router.delete("/{diary_id}", summary="일기 삭제")
async def delete_diary_api(diary_id: int):
    result = await delete_diary(diary_id)
    return result
