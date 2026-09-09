package tech.medo.infrastructure.secondary.fileupload.uploadedfile.storage

import java.io.InputStream
import java.util.UUID

interface UploadedFileStorage {
    fun save(
        fileId: UUID,
        originalFileName: String,
        contentType: String?,
        inputStream: InputStream
    ): StoredUploadedFile

    fun metadata(location: String): StoredUploadedFile?

    fun metadata(fileId: UUID): StoredUploadedFile?

    fun open(location: String): UploadedFileContent?

    fun open(fileId: UUID): UploadedFileContent?
}

data class StoredUploadedFile(
    val fileId: UUID,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long,
    val location: String,
    val checksum: String
)

data class UploadedFileContent(
    val metadata: StoredUploadedFile,
    val inputStream: InputStream
)
