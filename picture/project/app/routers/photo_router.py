from fastapi import APIRouter
from app.schemas.photo_schema import PhotoCreate
from app.services.photo_service import (
    create_photo,
    get_photo,
    list_photos_by_album,
    delete_photo
)

router = APIRouter()


@router.post("/", summary="사진 생성")
def create_photo_api(payload: PhotoCreate):
    return create_photo(payload.dict())


@router.get("/{photo_id}", summary="사진 상세 조회")
def get_photo_api(photo_id: int):
    return get_photo(photo_id)


@router.get("/album/{album_id}", summary="앨범별 사진 목록")
def list_photos_album(album_id: int):
    return list_photos_by_album(album_id)


@router.delete("/{photo_id}", summary="사진 삭제 (soft delete)")
def delete_photo_api(photo_id: int):
    return delete_photo(photo_id)
