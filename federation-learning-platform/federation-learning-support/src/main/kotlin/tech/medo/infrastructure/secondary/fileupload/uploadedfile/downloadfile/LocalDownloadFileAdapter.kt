package tech.medo.infrastructure.secondary.fileupload.uploadedfile.downloadfile

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tech.medo.fileupload.downloadfile.DownloadFileInput
import tech.medo.fileupload.downloadfile.DownloadFileResult
import tech.medo.fileupload.downloadfile.DownloadFileService
import tech.medo.infrastructure.secondary.fileupload.uploadedfile.storage.UploadedFileStorage

@Component
class LocalDownloadFileAdapter(
    private val storage: UploadedFileStorage,
    @Value("\${file-upload.public-base-url:http://localhost:\${server.port:8080}}")
    private val publicBaseUrl: String
) : DownloadFileService {
    override fun execute(input: DownloadFileInput): DownloadFileResult {
        val metadata = checkNotNull(storage.metadata(input.fileId)) {
            "Stored file ${input.fileId} is not available for download."
        }
        return DownloadFileResult.Succeeded(
            originalFileName = metadata.originalFileName,
            contentType = metadata.contentType,
            sizeBytes = metadata.sizeBytes,
            downloadUri = "${publicBaseUrl.trimEnd('/')}/api/files/${input.fileId}/content"
        )
    }
}
