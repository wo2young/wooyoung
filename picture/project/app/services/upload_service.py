# app/services/upload_service.py

import os
from uuid import uuid4
from fastapi import UploadFile

UPLOAD_DIR = "uploads"


async def save_file_locally(file: UploadFile) -> str:
    ext = os.path.splitext(file.filename)[1]
    filename = f"{uuid4().hex}{ext}"

    if not os.path.exists(UPLOAD_DIR):
        os.makedirs(UPLOAD_DIR)

    filepath = os.path.join(UPLOAD_DIR, filename)

    # async 방식으로 파일 읽기
    content = await file.read()

    # 파일 저장 (동기 I/O지만 FastAPI에서 허용됨)
    with open(filepath, "wb") as f:
        f.write(content)

    return f"/uploads/{filename}"
