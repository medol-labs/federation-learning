package tech.medo.dictionarymaintenance.dictionaryvaluecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class DictionaryValueCatalogReadModelQuery

class DictionaryValueCatalogReadModelCriteria {
    var dictionaryValueId: StringFilter? = null
    var dictionaryId: StringFilter? = null
    var dictionaryCode: StringFilter? = null
    var valueCode: StringFilter? = null
    var defaultDisplayName: StringFilter? = null
    var displayOrder: IntegerFilter? = null
    var description: StringFilter? = null
    var active: BooleanFilter? = null
    var state: Filter<DictionaryValueStateEnum>? = null
    var addedAt: RangeFilter<LocalDateTime>? = null
    var updatedAt: RangeFilter<LocalDateTime>? = null
    var disabledAt: RangeFilter<LocalDateTime>? = null
    var disabledReason: StringFilter? = null
    var enabledAt: RangeFilter<LocalDateTime>? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class DictionaryValueCatalogReadModelProjection : MetadataProjection {
    var dictionaryValueId: UUID? = null
    var dictionaryId: UUID? = null
    var dictionaryCode: String? = null
    var valueCode: String? = null
    var defaultDisplayName: String? = null
    var displayOrder: Int? = null
    var description: String? = null
    var active: Boolean? = null
    var state: DictionaryValueStateEnum? = null
    var addedAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null
    var disabledAt: LocalDateTime? = null
    var disabledReason: String? = null
    var enabledAt: LocalDateTime? = null
    override var projectionUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun DictionaryValueCatalogReadModelProjection.toReadModel(): DictionaryValueCatalogReadModel =
    DictionaryValueCatalogReadModel(
    dictionaryValueId = dictionaryValueId,
    dictionaryId = dictionaryId,
    dictionaryCode = dictionaryCode,
    valueCode = valueCode,
    defaultDisplayName = defaultDisplayName,
    displayOrder = displayOrder,
    description = description,
    active = active,
    state = state,
    addedAt = addedAt,
    updatedAt = updatedAt,
    disabledAt = disabledAt,
    disabledReason = disabledReason,
    enabledAt = enabledAt,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface DictionaryValueCatalogReadModelRepository {
    fun findAllByFilter(dictionaryCode: String?, active: Boolean?, state: DictionaryValueStateEnum?, pageable: Pageable): Page<DictionaryValueCatalogReadModel>
    fun findAllByCriteria(criteria: DictionaryValueCatalogReadModelCriteria?, pageable: Pageable): Page<DictionaryValueCatalogReadModel>
    fun findById(id: UUID): DictionaryValueCatalogReadModel?
    fun findProjectionById(id: UUID): DictionaryValueCatalogReadModelProjection?
    fun save(projection: DictionaryValueCatalogReadModelProjection)
}

data class DictionaryValueCatalogReadModel(
    val dictionaryValueId: UUID?,
    val dictionaryId: UUID?,
    val dictionaryCode: String?,
    val valueCode: String?,
    val defaultDisplayName: String?,
    val displayOrder: Int?,
    val description: String?,
    val active: Boolean?,
    val state: DictionaryValueStateEnum?,
    val addedAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val disabledAt: LocalDateTime?,
    val disabledReason: String?,
    val enabledAt: LocalDateTime?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
