from fastapi import APIRouter

router = APIRouter()

@router.get("/")
async def get_diaries():
    return {"message": "diary router working"}