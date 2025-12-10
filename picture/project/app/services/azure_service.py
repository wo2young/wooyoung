# app/services/azure_service.py

import uuid
from azure.storage.blob.aio import BlobServiceClient
from fastapi import UploadFile
from app.config import settings


# BlobServiceClient는 async 환경에서 이렇게 만들어야 함
blob_service_client = BlobServiceClient.from_connection_string(
    settings.AZURE_STORAGE_CONNECTION_STRING
)


async def upload_to_azure(file: UploadFile) -> str:
    """
    async 버전: 파일을 Azure Blob Storage에 업로드하고 public URL을 반환한다.
    """

    # 파일 확장자 추출
    ext = file.filename.split(".")[-1]
    filename = f"{uuid.uuid4().hex}.{ext}"

    # 컨테이너 클라이언트 가져오기
    container_client = blob_service_client.get_container_client(
        settings.AZURE_STORAGE_CONTAINER_NAME
    )

    # 업로드할 블롭 클라이언트
    blob_client = container_client.get_blob_client(filename)

    # 파일 읽기 (UploadFile 은 async read 필요)
    file_bytes = await file.read()

    # Blob 업로드 (async)
    await blob_client.upload_blob(file_bytes, overwrite=True)

    # Public URL 반환
    return blob_client.url
