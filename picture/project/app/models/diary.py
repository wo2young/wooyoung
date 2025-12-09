from sqlalchemy import Column, Integer, String, Text, Date
from app.database import Base

class Diary(Base):
    __tablename__ = "diaries"

    id = Column(Integer, primary_key=True, index=True)
    title = Column(String, nullable=False)
    content = Column(Text, nullable=True)
    date = Column(Date, nullable=False)
