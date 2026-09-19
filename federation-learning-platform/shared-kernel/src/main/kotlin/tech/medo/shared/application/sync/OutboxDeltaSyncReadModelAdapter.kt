package tech.medo.shared.application.sync

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDateTime

@Component
class OutboxDeltaSyncReadModelAdapter(
    private val properties: SyncReadModelProperties,
    @param:Qualifier("medolInternalRestClient") private val restClient: RestClient,
    private val objectMapper: ObjectMapper
) : SyncReadModelAdapter {
    private val log = LoggerFactory.getLogger(javaClass)
    private val mapType = object : TypeReference<Map<String, Any?>>() {}

    override fun supports(mode: String): Boolean =
        mode.equals("outbox-delta", ignoreCase = true)

    override fun syncOnce(target: SyncReadModelTarget, checkpoint: SyncReadModelCheckpoint?): SyncReadModelResult {
        val sourceBaseUrl = properties.sourceBaseUrlFor(target)
        if (sourceBaseUrl.isBlank()) {
            throw IllegalStateException("Sync target ${target.name} requires medol.sync.source-base-url")
        }

        var count = 0
        val context = SyncReadModelContext(properties, checkpoint)
        val queryParameters = target.queryParameters(context)
        var bootstrapHighWatermark: Long? = null
        if (checkpoint?.bootstrapCompleted != true) {
            var cursor: String? = null
            do {
                val snapshotUriBuilder = UriComponentsBuilder
                    .fromHttpUrl(sourceBaseUrl)
                    .path(target.sourcePath)
                    .queryParam("size", properties.pageSize)
                cursor?.takeIf { it.isNotBlank() }?.let { snapshotUriBuilder.queryParam("cursor", it) }
                queryParameters.forEach { (name, value) -> snapshotUriBuilder.queryParam(name, value) }

                log.debug(
                    "SYNC READMODEL snapshot pull target={} path={} cursor={} parameters={}",
                    target.name,
                    target.sourcePath,
                    cursor,
                    queryParameters
                )

                val snapshotResponse = restClient.get()
                    .uri(snapshotUriBuilder.toUriString())
                    .retrieve()
                    .body(JsonNode::class.java)

                val snapshotSyncedAt = LocalDateTime.now()
                val snapshotItems = snapshotResponse.itemsNode().toList()
                snapshotItems.forEach { item ->
                    target.upsert(objectMapper.convertValue(item, mapType), snapshotSyncedAt)
                    count += 1
                }
                bootstrapHighWatermark = snapshotResponse?.get("highWatermarkSequence")?.takeIf { !it.isNull }?.asLong()
                    ?: bootstrapHighWatermark
                cursor = snapshotResponse?.get("nextCursor")?.takeIf { !it.isNull }?.asText()
                if (snapshotItems.isNotEmpty()) {
                    log.info(
                        "SYNC READMODEL snapshot stored target={} itemCount={} highWatermarkSequence={} nextCursor={}",
                        target.name,
                        snapshotItems.size,
                        bootstrapHighWatermark,
                        cursor
                    )
                } else {
                    log.debug(
                        "SYNC READMODEL snapshot empty target={} highWatermarkSequence={} nextCursor={}",
                        target.name,
                        bootstrapHighWatermark,
                        cursor
                    )
                }
            } while (!cursor.isNullOrBlank())
        }

        val afterSequence = bootstrapHighWatermark ?: checkpoint?.lastSequence ?: 0
        val uriBuilder = UriComponentsBuilder
            .fromHttpUrl(sourceBaseUrl)
            .path(target.deltaPath)
            .queryParam("afterSequence", afterSequence)
            .queryParam("size", properties.pageSize)

        queryParameters.forEach { (name, value) -> uriBuilder.queryParam(name, value) }

        log.debug(
            "SYNC READMODEL delta pull target={} path={} afterSequence={} parameters={}",
            target.name,
            target.deltaPath,
            afterSequence,
            queryParameters
        )

        val response = restClient.get()
            .uri(uriBuilder.toUriString())
            .retrieve()
            .body(JsonNode::class.java)

        val syncedAt = LocalDateTime.now()
        val deltaItems = response.itemsNode().toList()
        var storedDeltaCount = 0
        deltaItems.forEach { item ->
            val operation = item.get("operation")?.asText() ?: "UPSERT"
            if (operation.equals("UPSERT", ignoreCase = true)) {
                val payload = item.get("payload") ?: item
                target.upsert(objectMapper.convertValue(payload, mapType), syncedAt)
                count += 1
                storedDeltaCount += 1
            }
        }

        val nextSequence = response?.get("nextSequence")?.takeIf { !it.isNull }?.asLong()
            ?: checkpoint?.lastSequence
        if (deltaItems.isNotEmpty()) {
            log.info(
                "SYNC READMODEL delta stored target={} pulledItemCount={} storedItemCount={} afterSequence={} nextSequence={}",
                target.name,
                deltaItems.size,
                storedDeltaCount,
                afterSequence,
                nextSequence
            )
        } else {
            log.debug(
                "SYNC READMODEL delta empty target={} afterSequence={} nextSequence={}",
                target.name,
                afterSequence,
                nextSequence
            )
        }
        return SyncReadModelResult(target.name, count, nextSequence = nextSequence)
    }

    private fun JsonNode?.itemsNode(): Iterable<JsonNode> {
        if (this == null || this.isNull) return emptyList()
        val items = this.get("items") ?: this.get("content") ?: this
        return if (items.isArray) items.toList() else emptyList()
    }
}
