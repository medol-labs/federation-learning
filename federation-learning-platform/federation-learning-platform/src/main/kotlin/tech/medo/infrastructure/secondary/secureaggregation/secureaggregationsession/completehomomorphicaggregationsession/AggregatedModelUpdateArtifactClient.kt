package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.net.URI

interface AggregatedModelUpdateArtifactClient {
    fun download(supportEndpoint: String, artifactRef: String): ByteArray
}

@Component
class RestClientAggregatedModelUpdateArtifactClient(
    restClientBuilder: RestClient.Builder
) : AggregatedModelUpdateArtifactClient {
    private val restClient = restClientBuilder.build()

    override fun download(supportEndpoint: String, artifactRef: String): ByteArray =
        requireNotNull(
            restClient.get()
                .uri(resolveArtifactUri(supportEndpoint, artifactRef))
                .retrieve()
                .body(ByteArray::class.java)
        ) { "Support returned an empty model update artifact for $artifactRef." }

    private fun resolveArtifactUri(supportEndpoint: String, artifactRef: String): URI {
        val ref = URI.create(artifactRef)
        if (!ref.isAbsolute) {
            return URI.create("${supportEndpoint.trimEnd('/')}/${artifactRef.trimStart('/')}")
        }
        if (ref.host == "localhost" || ref.host == "127.0.0.1") {
            val support = URI.create(supportEndpoint.trimEnd('/'))
            return URI.create("${support.scheme}://${support.authority}${ref.rawPath}${ref.rawQuery?.let { "?$it" } ?: ""}")
        }
        return ref
    }
}
