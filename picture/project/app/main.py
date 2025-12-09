from fastapi import FastAPI
from app.database import Base, engine
from app.routers import diary_router, photo_router

app = FastAPI(
    title="Picture Diary API",
    description="사진 + 일기 기록 서비스",
    version="1.0.0"
)

# ★ async 엔진에서는 이렇게 테이블 생성
@app.on_event("startup")
async def on_startup():
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.create_all)


# 라우터 등록
app.include_router(photo_router.router, prefix="/photos", tags=["Photos"])
app.include_router(diary_router.router, prefix="/diaries", tags=["Diaries"])


@app.get("/")
async def root():
    return {"message": "Picture Diary API is running!"}
