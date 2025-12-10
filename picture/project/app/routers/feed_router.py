from fastapi import APIRouter
from app.services.feed_service import (
    get_feed_latest,
    get_feed_random,
    get_feed_all
)

router = APIRouter(prefix="/feed", tags=["Feed"])


@router.get("/latest/{user_id}", summary="최신 피드 조회")
async def api_feed_latest(user_id: int):
    return await get_feed_latest(user_id)


@router.get("/random/{user_id}", summary="랜덤 피드 조회")
async def api_feed_random(user_id: int):
    return await get_feed_random(user_id)


@router.get("/all/{user_id}", summary="전체 피드 조회")
async def api_feed_all(user_id: int):
    return await get_feed_all(user_id)
