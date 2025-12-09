from sqlalchemy import Column, Integer, String, DateTime
from app.database import Base

class Photo(Base):
    __tablename__ = "photos"

    id = Column(Integer, primary_key=True, index=True)
    file_path = Column(String, nullable=False)
    location = Column(String, nullable=True)
    taken_at = Column(DateTime, nullable=True)
