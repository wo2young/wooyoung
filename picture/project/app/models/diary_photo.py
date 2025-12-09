from sqlalchemy import Column, Integer, ForeignKey
from app.database import Base

class DiaryPhoto(Base):
    __tablename__ = "diary_photos"

    id = Column(Integer, primary_key=True, index=True)
    diary_id = Column(Integer, ForeignKey("diaries.id"), nullable=False)
    photo_id = Column(Integer, ForeignKey("photos.id"), nullable=False)
