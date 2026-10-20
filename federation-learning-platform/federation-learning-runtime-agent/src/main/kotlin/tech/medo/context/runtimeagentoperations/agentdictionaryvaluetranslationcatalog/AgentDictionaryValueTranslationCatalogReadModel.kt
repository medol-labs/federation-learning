package tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class AgentDictionaryValueTranslationCatalogReadModelQuery

class AgentDictionaryValueTranslationCatalogReadModelCriteria {
    var dictionaryValueTranslationId: StringFilter? = null
    var dictionaryValueId: StringFilter? = null
    var dictionaryCode: StringFilter? = null
    var valueCode: StringFilter? = null
    var locale: StringFilter? = null
    var displayName: StringFilter? = null
    var description: StringFilter? = null
    var syncedAt: RangeFilter<LocalDateTime>? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class AgentDictionaryValueTranslationCatalogReadModelProjection : MetadataProjection {
    var dictionaryValueTranslationId: UUID? = null
    var dictionaryValueId: UUID? = null
    var dictionaryCode: String? = null
    var valueCode: String? = null
    var locale: String? = null
    var displayName: String? = null
    var description: String? = null
    var syncedAt: LocalDateTime? = null
    var projectionUpdatedAt: LocalDateTime? = null
    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    override fun assignMetadata(metadata: tech.medo.shared.application.metadata.MetadataSnapshot, updateTimestamp: Boolean) {
        if (updateTimestamp) this.projectionUpdatedAt = metadata.projectionUpdatedAt
        this.userId = metadata.userId
        this.sessionId = metadata.sessionId
        this.correlationId = metadata.correlationId
        this.causationId = metadata.causationId
        this.traceId = metadata.traceId
        this.tenantId = metadata.tenantId
    }
}

fun AgentDictionaryValueTranslationCatalogReadModelProjection.toReadModel(): AgentDictionaryValueTranslationCatalogReadModel =
    AgentDictionaryValueTranslationCatalogReadModel(
    dictionaryValueTranslationId = dictionaryValueTranslationId,
    dictionaryValueId = dictionaryValueId,
    dictionaryCode = dictionaryCode,
    valueCode = valueCode,
    locale = locale,
    displayName = displayName,
    description = description,
    syncedAt = syncedAt,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentDictionaryValueTranslationCatalogReadModelRepository {
    fun findAllByFilter(dictionaryCode: String?, valueCode: String?, locale: String?, pageable: Pageable): Page<AgentDictionaryValueTranslationCatalogReadModel>
    fun findAllByCriteria(criteria: AgentDictionaryValueTranslationCatalogReadModelCriteria?, pageable: Pageable): Page<AgentDictionaryValueTranslationCatalogReadModel>
    fun findById(id: UUID): AgentDictionaryValueTranslationCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): AgentDictionaryValueTranslationCatalogReadModel?
    fun findProjectionById(id: UUID): AgentDictionaryValueTranslationCatalogReadModelProjection?
    fun save(projection: AgentDictionaryValueTranslationCatalogReadModelProjection)
}

data class AgentDictionaryValueTranslationCatalogReadModel(
    val dictionaryValueTranslationId: UUID?,
    val dictionaryValueId: UUID?,
    val dictionaryCode: String?,
    val valueCode: String?,
    val locale: String?,
    val displayName: String?,
    val description: String?,
    val syncedAt: LocalDateTime?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
