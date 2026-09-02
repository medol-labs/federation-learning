package tech.medo.infrastructure.secondary.fileupload.uploadedfile.uploadfile

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tech.medo.fileupload.uploadfile.UploadFileStoredUploadedFile
import tech.medo.fileupload.uploadfile.UploadFileUploadedFileStorage
import tech.medo.infrastructure.secondary.fileupload.uploadedfile.storage.UploadedFileStorage
import java.io.InputStream
import java.time.LocalDateTime
import java.util.UUID

@Component
class LocalUploadFileAdapter(
    private val storage: UploadedFileStorage,
    @Value("\${file-upload.local.expiration-minutes:60}")
    private val expirationMinutes: Long,
    @Value("\${file-upload.public-base-url:http://localhost:\${server.port:8080}}")
    private val publicBaseUrl: String
) : UploadFileUploadedFileStorage {
    private val log = LoggerFactory.getLogger(LocalUploadFileAdapter::class.java)

    override fun save(
        uploadId: String?,
        fieldName: String,
        originalFileName: String,
        contentType: String?,
        inputStream: InputStream
    ): UploadFileStoredUploadedFile {
        val fileId = uploadId
            ?.takeIf { it.isNotBlank() }
            ?.let(UUID::fromString)
            ?: UUID.randomUUID()
        val stored = storage.save(
            fileId = fileId,
            originalFileName = originalFileName,
            contentType = contentType,
            inputStream = inputStream
        )
        log.info(
            "Stored uploaded file locally. fileId={}, fieldName={}, location={}, sizeBytes={}",
            fileId,
            fieldName,
            stored.location,
            stored.sizeBytes
        )
        return UploadFileStoredUploadedFile(
            location = "${publicBaseUrl.trimEnd('/')}/api/files/$fileId/content",
            originalFileName = stored.originalFileName,
            contentType = stored.contentType,
            sizeBytes = stored.sizeBytes,
            checksum = stored.checksum,
            expiresAt = LocalDateTime.now().plusMinutes(expirationMinutes)
        )
    }
}
