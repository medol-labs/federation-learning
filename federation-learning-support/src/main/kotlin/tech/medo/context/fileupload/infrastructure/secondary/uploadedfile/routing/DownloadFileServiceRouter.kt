package tech.medo.fileupload.infrastructure.secondary.uploadedfile.routing

import tech.medo.fileupload.downloadfile.DownloadFileInput
import tech.medo.fileupload.downloadfile.DownloadFileService
import tech.medo.fileupload.downloadfile.DownloadFileResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class DownloadFileServiceRouter(private val adapters: ObjectProvider<DownloadFileService>) : DownloadFileService {
    override fun supports(input: DownloadFileInput): Boolean = true

    override fun execute(input: DownloadFileInput): DownloadFileResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                throw ex
            }
            0 -> error("No DownloadFileService adapter supports the requested input.")
            else -> error("Multiple DownloadFileService adapters support the requested input.")
        }
    }
}
