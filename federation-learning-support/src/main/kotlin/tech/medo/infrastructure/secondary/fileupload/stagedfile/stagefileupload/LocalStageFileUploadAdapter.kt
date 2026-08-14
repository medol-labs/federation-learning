package tech.medo.infrastructure.secondary.fileupload.stagedfile.stagefileupload

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tech.medo.fileupload.stagefileupload.StageFileUploadStoredUploadedFile
import tech.medo.fileupload.stagefileupload.StageFileUploadUploadedFileStorage
import tech.medo.infrastructure.secondary.fileupload.stagedfile.storage.StagedFileStorage
import java.io.InputStream
import java.time.LocalDateTime
import java.util.UUID

@Component
class LocalStageFileUploadAdapter(
    private val storage: StagedFileStorage,
    @Value("\${file-upload.local.expiration-minutes:60}")
    private val expirationMinutes: Long
) : StageFileUploadUploadedFileStorage {
    private val log = LoggerFactory.getLogger(LocalStageFileUploadAdapter::class.java)

    override fun save(
        uploadId: String?,
        fieldName: String,
        originalFileName: String,
        contentType: String?,
        inputStream: InputStream
    ): StageFileUploadStoredUploadedFile {
        val stagedFileId = uploadId
            ?.takeIf { it.isNotBlank() }
            ?.let(UUID::fromString)
            ?: UUID.randomUUID()
        val stored = storage.save(
            stagedFileId = stagedFileId,
            originalFileName = originalFileName,
            contentType = contentType,
            inputStream = inputStream
        )
        log.info(
            "Stored uploaded file locally. stagedFileId={}, fieldName={}, location={}, sizeBytes={}",
            stagedFileId,
            fieldName,
            stored.location,
            stored.sizeBytes
        )
        return StageFileUploadStoredUploadedFile(
            location = stored.location,
            originalFileName = stored.originalFileName,
            contentType = stored.contentType,
            sizeBytes = stored.sizeBytes,
            checksum = stored.checksum,
            expiresAt = LocalDateTime.now().plusMinutes(expirationMinutes)
        )
    }
}
