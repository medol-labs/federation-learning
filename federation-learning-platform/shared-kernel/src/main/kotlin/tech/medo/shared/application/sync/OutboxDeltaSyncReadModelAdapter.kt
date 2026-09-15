package tech.medo.shared.application.sync

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDateTime

@Component
class OutboxDeltaSyncReadModelAdapter(
    private val properties: SyncReadModelProperties,
    restClientBuilder: RestClient.Builder,
    private val objectMapper: ObjectMapper
) : SyncReadModelAdapter {
    private val restClient: RestClient = restClientBuilder.build()
    private val mapType = object : TypeReference<Map<String, Any?>>() {}

    override fun supports(mode: String): Boolean =
        mode.equals("outbox-delta", ignoreCase = true)

    override fun syncOnce(target: SyncReadModelTarget, checkpoint: SyncReadModelCheckpoint?): SyncReadModelResult {
        if (properties.sourceBaseUrl.isBlank()) {
            throw IllegalStateException("Sync target ${target.name} requires medol.sync.source-base-url")
        }

        var count = 0
        val context = SyncReadModelContext(properties, checkpoint)
        var bootstrapHighWatermark: Long? = null
        if (checkpoint?.bootstrapCompleted != true) {
            var cursor: String? = null
            do {
                val snapshotUriBuilder = UriComponentsBuilder
                    .fromHttpUrl(properties.sourceBaseUrl)
                    .path(target.sourcePath)
                    .queryParam("size", properties.pageSize)
                cursor?.takeIf { it.isNotBlank() }?.let { snapshotUriBuilder.queryParam("cursor", it) }
                target.queryParameters(context).forEach { (name, value) -> snapshotUriBuilder.queryParam(name, value) }

                val snapshotResponse = restClient.get()
                    .uri(snapshotUriBuilder.toUriString())
                    .retrieve()
                    .body(JsonNode::class.java)

                val snapshotSyncedAt = LocalDateTime.now()
                snapshotResponse.itemsNode().forEach { item ->
                    target.upsert(objectMapper.convertValue(item, mapType), snapshotSyncedAt)
                    count += 1
                }
                bootstrapHighWatermark = snapshotResponse?.get("highWatermarkSequence")?.takeIf { !it.isNull }?.asLong()
                    ?: bootstrapHighWatermark
                cursor = snapshotResponse?.get("nextCursor")?.takeIf { !it.isNull }?.asText()
            } while (!cursor.isNullOrBlank())
        }

        val afterSequence = bootstrapHighWatermark ?: checkpoint?.lastSequence ?: 0
        val uriBuilder = UriComponentsBuilder
            .fromHttpUrl(properties.sourceBaseUrl)
            .path(target.deltaPath)
            .queryParam("afterSequence", afterSequence)
            .queryParam("size", properties.pageSize)

        target.queryParameters(context).forEach { (name, value) -> uriBuilder.queryParam(name, value) }

        val response = restClient.get()
            .uri(uriBuilder.toUriString())
            .retrieve()
            .body(JsonNode::class.java)

        val syncedAt = LocalDateTime.now()
        response.itemsNode().forEach { item ->
            val operation = item.get("operation")?.asText() ?: "UPSERT"
            if (operation.equals("UPSERT", ignoreCase = true)) {
                val payload = item.get("payload") ?: item
                target.upsert(objectMapper.convertValue(payload, mapType), syncedAt)
                count += 1
            }
        }

        val nextSequence = response?.get("nextSequence")?.takeIf { !it.isNull }?.asLong()
            ?: checkpoint?.lastSequence
        return SyncReadModelResult(target.name, count, nextSequence = nextSequence)
    }

    private fun JsonNode?.itemsNode(): Iterable<JsonNode> {
        if (this == null || this.isNull) return emptyList()
        val items = this.get("items") ?: this.get("content") ?: this
        return if (items.isArray) items.toList() else emptyList()
    }
}
