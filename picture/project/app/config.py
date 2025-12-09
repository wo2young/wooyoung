from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    PROJECT_NAME: str = "FastAPI Project"
    DATABASE_URL: str

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"   # ← 여기로 UTF-8 강제

settings = Settings()
