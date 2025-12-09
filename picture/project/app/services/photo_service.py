from pydantic import BaseModel
from datetime import datetime

class PhotoCreate(BaseModel):
    location: str | None = None
    taken_at: datetime | None = None

class PhotoResponse(BaseModel):
    id: int
    file_path: str
    location: str | None
    taken_at: datetime | None

    class Config:
        from_attributes = True