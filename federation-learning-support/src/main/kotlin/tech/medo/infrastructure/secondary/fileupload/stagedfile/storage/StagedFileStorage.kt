package tech.medo.infrastructure.secondary.fileupload.stagedfile.storage

import java.io.InputStream
import java.util.UUID

interface StagedFileStorage {
    fun save(
        stagedFileId: UUID,
        originalFileName: String,
        contentType: String?,
        inputStream: InputStream
    ): StoredStagedFile

    fun metadata(location: String): StoredStagedFile?

    fun open(location: String): StagedFileContent?
}

data class StoredStagedFile(
    val stagedFileId: UUID,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long,
    val location: String,
    val checksum: String
)

data class StagedFileContent(
    val metadata: StoredStagedFile,
    val inputStream: InputStream
)
