import uuid
import os
from dotenv import load_dotenv
from fastapi import UploadFile
from azure.storage.blob import BlobServiceClient

load_dotenv()

AZURE_CONNECTION_STRING = os.getenv("AZURE_STORAGE_CONNECTION_STRING")
AZURE_CONTAINER = os.getenv("AZURE_STORAGE_CONTAINER_NAME")

# 클라이언트 생성
blob_service_client = BlobServiceClient.from_connection_string(AZURE_CONNECTION_STRING)
container_client = blob_service_client.get_container_client(AZURE_CONTAINER)


def upload_to_azure(file: UploadFile) -> str:
    """
    파일을 Azure Blob Storage에 업로드하고 public URL을 반환한다.
    """

    ext = file.filename.split(".")[-1]
    filename = f"{uuid.uuid4().hex}.{ext}"

    # Blob에 업로드
    blob_client = container_client.get_blob_client(filename)
    blob_client.upload_blob(file.file, overwrite=True)

    # Public URL 생성
    url = f"{blob_client.url}"

    return url
