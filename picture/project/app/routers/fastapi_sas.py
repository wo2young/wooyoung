# 예시: fastapi_sas.py

from datetime import datetime, timedelta
from fastapi import FastAPI
from pydantic import BaseModel
from azure.storage.blob import generate_blob_sas, BlobSasPermissions

app = FastAPI()

ACCOUNT_NAME = "your_storage_account_name"
ACCOUNT_KEY = "your_storage_account_key"
CONTAINER_NAME = "photos"  # 네가 쓰는 컨테이너 이름

class SasRequest(BaseModel):
    fileName: str
    contentType: str

@app.post("/api/photos/sas-url")
def get_upload_sas(request: SasRequest):
    blob_name = request.fileName

    sas_token = generate_blob_sas(
        account_name=ACCOUNT_NAME,
        container_name=CONTAINER_NAME,
        blob_name=blob_name,
        account_key=ACCOUNT_KEY,
        permission=BlobSasPermissions(write=True, create=True),
        expiry=datetime.utcnow() + timedelta(minutes=10),
        content_type=request.contentType,
    )

    blob_url = f"https://{ACCOUNT_NAME}.blob.core.windows.net/{CONTAINER_NAME}/{blob_name}"
    upload_url = f"{blob_url}?{sas_token}"

    return {
        "uploadUrl": upload_url,
        "blobUrl": blob_url,
    }
