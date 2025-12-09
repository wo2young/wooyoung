import os
from uuid import uuid4
from fastapi import UploadFile

UPLOAD_DIR = "uploads"


def save_file_locally(file: UploadFile) -> str:
    # 확장자 추출
    ext = os.path.splitext(file.filename)[1]
    filename = f"{uuid4().hex}{ext}"

    # uploads 폴더 생성
    if not os.path.exists(UPLOAD_DIR):
        os.makedirs(UPLOAD_DIR)

    filepath = os.path.join(UPLOAD_DIR, filename)

    # 파일 저장
    with open(filepath, "wb") as f:
        f.write(file.file.read())

    # 클라이언트에 반환할 URL
    file_url = f"/uploads/{filename}"
    return file_url
