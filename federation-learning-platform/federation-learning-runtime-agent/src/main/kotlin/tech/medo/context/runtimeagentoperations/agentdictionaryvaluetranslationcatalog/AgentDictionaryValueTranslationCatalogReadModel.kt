package tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

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
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
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
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
