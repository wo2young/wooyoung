from pydantic import BaseModel
from typing import Optional
from datetime import datetime, date


class DiaryCreate(BaseModel):
    family_id: int
    writer_id: int
    title: Optional[str] = None
    content: str
    diary_date: date


class DiaryUpdate(BaseModel):
    title: Optional[str] = None
    content: Optional[str] = None
    diary_date: Optional[date] = None


class DiaryResponse(BaseModel):
    id: int
    family_id: int
    writer_id: int
    title: Optional[str]
    content: str
    diary_date: date
    created_at: datetime

    class Config:
        orm_mode = True
