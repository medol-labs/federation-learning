package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.util.UUID

interface AggregatedModelFileUploadClient {
    fun upload(
        supportEndpoint: String,
        internalToken: String,
        fileId: UUID,
        fileName: String,
        contentType: String,
        content: ByteArray
    ): UploadedAggregatedModelFile
}

@Component
class RestClientAggregatedModelFileUploadClient(
    restClientBuilder: RestClient.Builder
) : AggregatedModelFileUploadClient {
    private val restClient = restClientBuilder.build()

    override fun upload(
        supportEndpoint: String,
        internalToken: String,
        fileId: UUID,
        fileName: String,
        contentType: String,
        content: ByteArray
    ): UploadedAggregatedModelFile {
        val resource = object : ByteArrayResource(content) {
            override fun getFilename(): String = fileName
        }
        val multipart = MultipartBodyBuilder().apply {
            part("fileId", fileId.toString())
            part("purpose", "FEDERATED_GLOBAL_MODEL")
            part("uploadedFile", resource).contentType(MediaType.parseMediaType(contentType))
        }
        return requireNotNull(
            restClient.post()
                .uri("${supportEndpoint.trimEnd('/')}/uploadedfile/uploadfile/file")
                .header(INTERNAL_TOKEN_HEADER, internalToken)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multipart.build())
                .retrieve()
                .body(UploadedAggregatedModelFile::class.java)
        ) { "Support returned an empty aggregated model file upload response." }
    }

    private companion object {
        private const val INTERNAL_TOKEN_HEADER = "X-MEDOL-INTERNAL-TOKEN"
    }
}

data class UploadedAggregatedModelFile(
    val fileId: UUID,
    val fileLocation: String,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val checksum: String?
) {
    val artifactUri: String get() = fileLocation
    val registryRef: String get() = fileLocation.substringBeforeLast("/content")
    val digest: String get() = requireNotNull(checksum) { "Stored aggregated model checksum is required." }
}
