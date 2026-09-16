package tech.medo.shared.application.sync

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder

@Component
class HttpPullSyncReadModelAdapter(
    private val properties: SyncReadModelProperties,
    restClientBuilder: RestClient.Builder,
    private val objectMapper: ObjectMapper
) : SyncReadModelAdapter {
    private val restClient: RestClient = restClientBuilder.build()
    private val mapType = object : TypeReference<Map<String, Any?>>() {}

    override fun supports(mode: String): Boolean =
        mode.equals("pull-http", ignoreCase = true)

    override fun syncOnce(target: SyncReadModelTarget, checkpoint: SyncReadModelCheckpoint?): SyncReadModelResult {
        if (properties.sourceBaseUrl.isBlank()) {
            throw IllegalStateException("Sync target ${target.name} requires medol.sync.source-base-url")
        }

        var cursor: String? = null
        var count = 0
        val syncedAt = java.time.LocalDateTime.now()

        do {
            val uriBuilder = UriComponentsBuilder
                .fromHttpUrl(properties.sourceBaseUrl)
                .path(target.sourcePath)
                .queryParam("size", properties.pageSize)
            val context = SyncReadModelContext(properties, checkpoint)
            target.queryParameters(context).forEach { (name, value) -> uriBuilder.queryParam(name, value) }
            checkpoint?.lastSuccessfulSyncedAt?.let { uriBuilder.queryParam("updatedAfter", it) }
            cursor?.let { uriBuilder.queryParam("cursor", it) }

            val response = restClient.get()
                .uri(uriBuilder.toUriString())
                .retrieve()
                .body(JsonNode::class.java)

            val items = response.itemsNode()
            items.forEach { item ->
                target.upsert(objectMapper.convertValue(item, mapType), syncedAt)
                count += 1
            }
            cursor = response?.get("nextCursor")?.takeIf { !it.isNull }?.asText()
        } while (!cursor.isNullOrBlank())

        return SyncReadModelResult(target.name, count, cursor)
    }

    private fun JsonNode?.itemsNode(): Iterable<JsonNode> {
        if (this == null || this.isNull) return emptyList()
        val items = this.get("items") ?: this.get("content") ?: this
        return if (items.isArray) items.toList() else emptyList()
    }
}
