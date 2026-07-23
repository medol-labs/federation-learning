package tech.medo.dictionarymaintenance.dictionaryvaluecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class DictionaryValueCatalogReadModelQuery

class DictionaryValueCatalogReadModelProjection : MetadataProjection {
    var dictionaryValueId: UUID? = null
    var dictionaryId: UUID? = null
    var dictionaryCode: String? = null
    var valueCode: String? = null
    var displayName: String? = null
    var displayOrder: Int? = null
    var description: String? = null
    var active: Boolean? = null
    var state: DictionaryValueStateEnum? = null
    var addedAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null
    var disabledAt: LocalDateTime? = null
    var disabledReason: String? = null
    var enabledAt: LocalDateTime? = null
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
    displayName = displayName,
    displayOrder = displayOrder,
    description = description,
    active = active,
    state = state,
    addedAt = addedAt,
    updatedAt = updatedAt,
    disabledAt = disabledAt,
    disabledReason = disabledReason,
    enabledAt = enabledAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface DictionaryValueCatalogReadModelRepository {
    fun findAllByFilter(dictionaryCode: String?, active: Boolean?, state: DictionaryValueStateEnum?, pageable: Pageable): Page<DictionaryValueCatalogReadModel>
    fun findById(id: UUID): DictionaryValueCatalogReadModel?
    fun findProjectionById(id: UUID): DictionaryValueCatalogReadModelProjection?
    fun save(projection: DictionaryValueCatalogReadModelProjection)
}

data class DictionaryValueCatalogReadModel(
    val dictionaryValueId: UUID?,
    val dictionaryId: UUID?,
    val dictionaryCode: String?,
    val valueCode: String?,
    val displayName: String?,
    val displayOrder: Int?,
    val description: String?,
    val active: Boolean?,
    val state: DictionaryValueStateEnum?,
    val addedAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val disabledAt: LocalDateTime?,
    val disabledReason: String?,
    val enabledAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
