from fastapi import APIRouter
from app.schemas.diary_schema import DiaryCreate, DiaryUpdate
from app.services.diary_service import (
    create_diary,
    get_diary,
    list_diaries_by_family,
    update_diary,
    delete_diary
)

router = APIRouter()


@router.post("/", summary="일기 생성")
def create_diary_api(payload: DiaryCreate):
    return create_diary(payload.dict())


@router.get("/{diary_id}", summary="일기 상세 조회")
def get_diary_api(diary_id: int):
    return get_diary(diary_id)


@router.get("/family/{family_id}", summary="가족별 일기 목록")
def list_diaries_api(family_id: int):
    return list_diaries_by_family(family_id)


@router.put("/{diary_id}", summary="일기 수정")
def update_diary_api(diary_id: int, payload: DiaryUpdate):
    return update_diary(diary_id, payload.dict())


@router.delete("/{diary_id}", summary="일기 삭제")
def delete_diary_api(diary_id: int):
    return delete_diary(diary_id)
