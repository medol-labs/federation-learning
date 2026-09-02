package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.submitagentlocalmodelupdate

import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.nio.file.Path
import java.util.UUID

interface SupportFileUploadClient {
    fun upload(fileId: UUID, path: Path): UploadedFile
}

@Component
class RestClientSupportFileUploadClient(
    restClientBuilder: RestClient.Builder,
    private val properties: LocalModelUpdateSubmissionProperties
) : SupportFileUploadClient {
    private val restClient = restClientBuilder.build()

    override fun upload(fileId: UUID, path: Path): UploadedFile {
        val multipart = MultipartBodyBuilder().apply {
            part("fileId", fileId.toString())
            part("purpose", "LOCAL_MODEL_UPDATE")
            part("uploadedFile", FileSystemResource(path))
        }
        return requireNotNull(
            restClient.post()
                .uri("${properties.supportUrl.trimEnd('/')}/uploadedfile/uploadfile/file")
                .header(INTERNAL_TOKEN_HEADER, properties.internalToken)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multipart.build())
                .retrieve()
                .body(UploadedFile::class.java)
        ) { "Support returned an empty file upload response." }
    }

    private companion object {
        private const val INTERNAL_TOKEN_HEADER = "X-MEDOL-INTERNAL-TOKEN"
    }
}

data class UploadedFile(
    val fileId: UUID,
    val fileLocation: String,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val checksum: String?
) {
    val artifactUri: String get() = fileLocation
    val digest: String get() = requireNotNull(checksum) { "Stored model update checksum is required." }
}
