package tech.medo.fileupload.downloadfile

import java.util.UUID;

interface DownloadFileService {
    fun supports(input: DownloadFileInput): Boolean = true
    fun execute(input: DownloadFileInput): DownloadFileResult
}

data class DownloadFileInput(
    val fileId: UUID
)

sealed interface DownloadFileResult {
    data class Succeeded(
        val originalFileName: String,
        val contentType: String?,
        val sizeBytes: Long?,
        val downloadUri: String
    ) : DownloadFileResult


}
