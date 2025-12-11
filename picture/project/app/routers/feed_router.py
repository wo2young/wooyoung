from fastapi import APIRouter
from app.services.feed_service import (
    get_feed_latest,
    get_feed_random,
    get_feed_all
)

router = APIRouter(prefix="/feed", tags=["Feed"])


@router.get("/latest")
def latest_feed(user_id: int):
    return get_feed_latest(user_id)


@router.get("/random")
def random_feed(user_id: int):
    return get_feed_random(user_id)


@router.get("/all")
def all_feed(user_id: int):
    return get_feed_all(user_id)
