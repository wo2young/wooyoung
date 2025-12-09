from fastapi import APIRouter, UploadFile, File
from app.services.upload_service import save_file_locally
from app.services.azure_service import upload_to_azure

router = APIRouter(prefix="/upload", tags=["Upload"])


@router.post("/photo")
def upload_photo(file: UploadFile = File(...)):
    url = save_file_locally(file)
    return {"url": url}

@router.post("/photo/local")
def upload_photo_local(file: UploadFile = File(...)):
    url = save_file_locally(file)
    return {"url": url}


@router.post("/photo/azure")
def upload_photo_azure(file: UploadFile = File(...)):
    url = upload_to_azure(file)
    return {"url": url}
