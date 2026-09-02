package tech.medo.fileupload.uploadedfile

import java.util.UUID;


data class UploadedFileSelection(
    val fileId: UUID
)

object UploadedFileTags {
    const val FILE_ID = "fileId"
}

object UploadedFileMetadata {
    val concepts = listOf("UploadedFile")
}
