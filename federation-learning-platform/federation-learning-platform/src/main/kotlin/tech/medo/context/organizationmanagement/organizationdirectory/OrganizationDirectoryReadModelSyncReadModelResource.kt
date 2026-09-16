package tech.medo.organizationmanagement.organizationdirectory

import org.springframework.data.domain.PageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import tech.medo.shared.application.sync.SyncOutboxRepository

@CrossOrigin
@RestController
@RequestMapping("/sync/read-models/organization-management/organization-directory")
class OrganizationDirectoryReadModelSyncReadModelResource(
    private val repository: OrganizationDirectoryReadModelRepository,
    private val outboxRepository: SyncOutboxRepository,
    private val objectMapper: ObjectMapper
) {
    private val mapType = object : TypeReference<Map<String, Any?>>() {}

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('organization_directory:list') or hasAuthority('organization_directory:read')")
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
            .findFirstBySourceContextAndSourceReadModelOrderBySequenceDesc("OrganizationManagement", "OrganizationDirectory")
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

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('organization_directory:list') or hasAuthority('organization_directory:read')")
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
                "OrganizationManagement",
                "OrganizationDirectory",
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
                    "channel" to row.channel,
                    "sourceContext" to row.sourceContext,
                    "sourceReadModel" to row.sourceReadModel,
                    "readModelKey" to row.messageKey,
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
