package tech.medo.datasetgovernance.featureschemacatalog

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tech.medo.shared.application.sync.SyncReadModelOutboxRepository

@CrossOrigin
@RestController
@RequestMapping("/sync/read-models/dataset-governance/feature-schema-catalog")
class FeatureSchemaCatalogReadModelSyncReadModelResource(
    private val repository: FeatureSchemaCatalogReadModelRepository,
    private val outboxRepository: SyncReadModelOutboxRepository,
    private val objectMapper: ObjectMapper
) {
    private val mapType = object : TypeReference<Map<String, Any?>>() {}

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('feature_schema_catalog:list') or hasAuthority('feature_schema_catalog:read')")
    @GetMapping
    fun findAllForSync(
        @RequestParam(defaultValue = "200") size: Int,
        @RequestParam(required = false) cursor: String?,
        @RequestParam parameters: Map<String, String>
    ): Map<String, Any?> {
        val reserved = setOf("afterSequence", "size", "cursor")
        val filters = parameters.filterKeys { it !in reserved }
        val pageNumber = cursor?.toIntOrNull()?.coerceAtLeast(0) ?: 0
        val page = repository.findAll(PageRequest.of(pageNumber, size.coerceIn(1, 1000)))
        val highWatermark = outboxRepository
            .findFirstBySourceContextAndSourceReadModelOrderBySequenceDesc("DatasetGovernance", "FeatureSchemaCatalog")
            ?.sequence ?: 0
        val items = page.content.mapNotNull { item ->
            val payload = objectMapper.convertValue(item, mapType)
            if (filters.any { (name, value) -> payload[name]?.toString() != value }) {
                null
            } else {
                payload
            }
        }
        return mapOf(
            "items" to items,
            "nextCursor" to if (page.hasNext()) (pageNumber + 1).toString() else null,
            "highWatermarkSequence" to highWatermark
        )
    }

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('feature_schema_catalog:list') or hasAuthority('feature_schema_catalog:read')")
    @GetMapping("/deltas")
    fun findDeltasForSync(
        @RequestParam(defaultValue = "0") afterSequence: Long,
        @RequestParam(defaultValue = "200") size: Int,
        @RequestParam parameters: Map<String, String>
    ): Map<String, Any?> {
        val reserved = setOf("afterSequence", "size", "cursor")
        val filters = parameters.filterKeys { it !in reserved }
        val rows = outboxRepository
            .findBySourceContextAndSourceReadModelAndSequenceGreaterThanOrderBySequenceAsc(
                "DatasetGovernance",
                "FeatureSchemaCatalog",
                afterSequence,
                PageRequest.of(0, size.coerceIn(1, 1000))
            )
        val items = rows.mapNotNull { row ->
            val payload = objectMapper.readValue(row.payloadJson, mapType)
            if (filters.any { (name, value) -> payload[name]?.toString() != value }) {
                null
            } else {
                mapOf(
                    "sequence" to row.sequence,
                    "operation" to row.operation,
                    "sourceContext" to row.sourceContext,
                    "sourceReadModel" to row.sourceReadModel,
                    "readModelKey" to row.readModelKey,
                    "eventId" to row.eventId,
                    "eventType" to row.eventType,
                    "occurredAt" to row.occurredAt,
                    "payload" to payload
                )
            }
        }
        return mapOf(
            "items" to items,
            "nextSequence" to (rows.lastOrNull()?.sequence ?: afterSequence)
        )
    }
}
