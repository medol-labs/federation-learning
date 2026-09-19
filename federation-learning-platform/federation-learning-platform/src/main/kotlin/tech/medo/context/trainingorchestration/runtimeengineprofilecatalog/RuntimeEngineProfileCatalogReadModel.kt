package tech.medo.trainingorchestration.runtimeengineprofilecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeEngineProfileCatalogReadModelQuery

class RuntimeEngineProfileCatalogReadModelCriteria {
    var runtimeEngineProfileId: StringFilter? = null
    var profileName: StringFilter? = null
    var pluginProfile: StringFilter? = null
    var runtimeEngineImage: StringFilter? = null
    var imageDigest: StringFilter? = null
    var supportedModelPluginsDescription: StringFilter? = null
    var supportedAggregationAlgorithmsDescription: StringFilter? = null
    var active: BooleanFilter? = null
    var state: Filter<RuntimeEngineProfileStateEnum>? = null
    var registeredAt: RangeFilter<LocalDateTime>? = null
}


class RuntimeEngineProfileCatalogReadModelProjection : MetadataProjection {
    var runtimeEngineProfileId: UUID? = null
    var profileName: String? = null
    var pluginProfile: String? = null
    var runtimeEngineImage: String? = null
    var imageDigest: String? = null
    var supportedModelPluginsDescription: String? = null
    var supportedAggregationAlgorithmsDescription: String? = null
    var active: Boolean? = null
    var state: RuntimeEngineProfileStateEnum? = null
    var registeredAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeEngineProfileCatalogReadModelProjection.toReadModel(): RuntimeEngineProfileCatalogReadModel =
    RuntimeEngineProfileCatalogReadModel(
    runtimeEngineProfileId = runtimeEngineProfileId,
    profileName = profileName,
    pluginProfile = pluginProfile,
    runtimeEngineImage = runtimeEngineImage,
    imageDigest = imageDigest,
    supportedModelPluginsDescription = supportedModelPluginsDescription,
    supportedAggregationAlgorithmsDescription = supportedAggregationAlgorithmsDescription,
    active = active,
    state = state,
    registeredAt = registeredAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeEngineProfileCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeEngineProfileCatalogReadModel>
    fun findAllByCriteria(criteria: RuntimeEngineProfileCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeEngineProfileCatalogReadModel>
    fun findById(id: UUID): RuntimeEngineProfileCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeEngineProfileCatalogReadModelProjection?
    fun save(projection: RuntimeEngineProfileCatalogReadModelProjection)
}

data class RuntimeEngineProfileCatalogReadModel(
    val runtimeEngineProfileId: UUID?,
    val profileName: String?,
    val pluginProfile: String?,
    val runtimeEngineImage: String?,
    val imageDigest: String?,
    val supportedModelPluginsDescription: String?,
    val supportedAggregationAlgorithmsDescription: String?,
    val active: Boolean?,
    val state: RuntimeEngineProfileStateEnum?,
    val registeredAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
