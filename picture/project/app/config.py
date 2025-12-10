from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    PROJECT_NAME: str = "FastAPI Project"

    # 필수 환경 변수들
    DATABASE_URL: str                      # PostgreSQL 연결 정보
    AZURE_STORAGE_CONNECTION_STRING: str   # Azure Blob Storage 연결 문자열
    AZURE_STORAGE_CONTAINER_NAME: str      # Blob 컨테이너 이름

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"

settings = Settings()
