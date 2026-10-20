package tech.medo.dictionarymaintenance.dictionarycatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class DictionaryCatalogReadModelQuery

class DictionaryCatalogReadModelCriteria {
    var dictionaryId: StringFilter? = null
    var dictionaryCode: StringFilter? = null
    var dictionaryName: StringFilter? = null
    var description: StringFilter? = null
    var state: Filter<DictionaryStateEnum>? = null
    var registeredAt: RangeFilter<LocalDateTime>? = null
    var updatedAt: RangeFilter<LocalDateTime>? = null
    var archivedAt: RangeFilter<LocalDateTime>? = null
    var archiveReason: StringFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class DictionaryCatalogReadModelProjection : MetadataProjection {
    var dictionaryId: UUID? = null
    var dictionaryCode: String? = null
    var dictionaryName: String? = null
    var description: String? = null
    var state: DictionaryStateEnum? = null
    var registeredAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null
    var archivedAt: LocalDateTime? = null
    var archiveReason: String? = null
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

fun DictionaryCatalogReadModelProjection.toReadModel(): DictionaryCatalogReadModel =
    DictionaryCatalogReadModel(
    dictionaryId = dictionaryId,
    dictionaryCode = dictionaryCode,
    dictionaryName = dictionaryName,
    description = description,
    state = state,
    registeredAt = registeredAt,
    updatedAt = updatedAt,
    archivedAt = archivedAt,
    archiveReason = archiveReason,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface DictionaryCatalogReadModelRepository {
    fun findAllByFilter(dictionaryCode: String?, pageable: Pageable): Page<DictionaryCatalogReadModel>
    fun findAllByCriteria(criteria: DictionaryCatalogReadModelCriteria?, pageable: Pageable): Page<DictionaryCatalogReadModel>
    fun findById(id: UUID): DictionaryCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): DictionaryCatalogReadModel?
    fun findProjectionById(id: UUID): DictionaryCatalogReadModelProjection?
    fun save(projection: DictionaryCatalogReadModelProjection)
}

data class DictionaryCatalogReadModel(
    val dictionaryId: UUID?,
    val dictionaryCode: String?,
    val dictionaryName: String?,
    val description: String?,
    val state: DictionaryStateEnum?,
    val registeredAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val archivedAt: LocalDateTime?,
    val archiveReason: String?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
