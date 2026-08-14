package tech.medo.fileupload.stagefileupload

import java.io.InputStream
import java.time.LocalDateTime

interface StageFileUploadUploadedFileStorage {
    fun save(
        uploadId: String?,
        fieldName: String,
        originalFileName: String,
        contentType: String?,
        inputStream: InputStream
    ): StageFileUploadStoredUploadedFile
}

data class StageFileUploadStoredUploadedFile(
    val location: String,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val checksum: String?,
    val expiresAt: LocalDateTime
)
