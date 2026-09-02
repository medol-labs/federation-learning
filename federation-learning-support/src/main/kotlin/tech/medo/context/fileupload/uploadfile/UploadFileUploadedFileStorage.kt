package tech.medo.fileupload.uploadfile

import java.io.InputStream
import java.time.LocalDateTime

interface UploadFileUploadedFileStorage {
    fun save(
        uploadId: String?,
        fieldName: String,
        originalFileName: String,
        contentType: String?,
        inputStream: InputStream
    ): UploadFileStoredUploadedFile
}

data class UploadFileStoredUploadedFile(
    val location: String,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val checksum: String?,
    val expiresAt: LocalDateTime
)
