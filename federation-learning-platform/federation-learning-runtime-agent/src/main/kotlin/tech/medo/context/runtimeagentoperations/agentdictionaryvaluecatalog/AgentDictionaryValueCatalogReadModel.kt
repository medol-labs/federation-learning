package tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class AgentDictionaryValueCatalogReadModelQuery

class AgentDictionaryValueCatalogReadModelCriteria {
    var dictionaryValueId: StringFilter? = null
    var dictionaryId: StringFilter? = null
    var dictionaryCode: StringFilter? = null
    var valueCode: StringFilter? = null
    var displayName: StringFilter? = null
    var displayOrder: IntegerFilter? = null
    var active: BooleanFilter? = null
    var state: StringFilter? = null
    var syncedAt: RangeFilter<LocalDateTime>? = null
}


class AgentDictionaryValueCatalogReadModelProjection : MetadataProjection {
    var dictionaryValueId: UUID? = null
    var dictionaryId: UUID? = null
    var dictionaryCode: String? = null
    var valueCode: String? = null
    var displayName: String? = null
    var displayOrder: Int? = null
    var active: Boolean? = null
    var state: String? = null
    var syncedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun AgentDictionaryValueCatalogReadModelProjection.toReadModel(): AgentDictionaryValueCatalogReadModel =
    AgentDictionaryValueCatalogReadModel(
    dictionaryValueId = dictionaryValueId,
    dictionaryId = dictionaryId,
    dictionaryCode = dictionaryCode,
    valueCode = valueCode,
    displayName = displayName,
    displayOrder = displayOrder,
    active = active,
    state = state,
    syncedAt = syncedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentDictionaryValueCatalogReadModelRepository {
    fun findAllByFilter(dictionaryCode: String?, active: Boolean?, state: String?, pageable: Pageable): Page<AgentDictionaryValueCatalogReadModel>
    fun findAllByCriteria(criteria: AgentDictionaryValueCatalogReadModelCriteria?, pageable: Pageable): Page<AgentDictionaryValueCatalogReadModel>
    fun findById(id: UUID): AgentDictionaryValueCatalogReadModel?
    fun findProjectionById(id: UUID): AgentDictionaryValueCatalogReadModelProjection?
    fun save(projection: AgentDictionaryValueCatalogReadModelProjection)
}

data class AgentDictionaryValueCatalogReadModel(
    val dictionaryValueId: UUID?,
    val dictionaryId: UUID?,
    val dictionaryCode: String?,
    val valueCode: String?,
    val displayName: String?,
    val displayOrder: Int?,
    val active: Boolean?,
    val state: String?,
    val syncedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
