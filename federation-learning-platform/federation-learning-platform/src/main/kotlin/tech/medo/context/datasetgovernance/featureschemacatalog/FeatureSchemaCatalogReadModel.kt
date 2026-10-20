package tech.medo.datasetgovernance.featureschemacatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.datasetgovernance.domain.types.FeatureDefinition
import tech.medo.datasetgovernance.domain.types.LabelDefinition
import java.time.LocalDateTime
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class FeatureSchemaCatalogReadModelQuery

class FeatureSchemaCatalogReadModelCriteria {
    var featureSchemaId: StringFilter? = null
    var featureDomain: StringFilter? = null
    var version: StringFilter? = null
    var dataModality: StringFilter? = null
    var featureCount: IntegerFilter? = null
    var schemaStatus: StringFilter? = null
    var supersededByFeatureSchemaId: StringFilter? = null
    var recommendedForDomain: BooleanFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class FeatureSchemaCatalogReadModelProjection : MetadataProjection {
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var version: String? = null
    var dataModality: String? = null
    var features: List<FeatureDefinition> = emptyList()
    var labels: List<LabelDefinition> = emptyList()
    var featureCount: Int? = null
    var schemaStatus: String? = null
    var supersededByFeatureSchemaId: UUID? = null
    var recommendedForDomain: Boolean? = null
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

fun FeatureSchemaCatalogReadModelProjection.toReadModel(): FeatureSchemaCatalogReadModel =
    FeatureSchemaCatalogReadModel(
    featureSchemaId = featureSchemaId,
    featureDomain = featureDomain,
    version = version,
    dataModality = dataModality,
    features = features,
    labels = labels,
    featureCount = featureCount,
    schemaStatus = schemaStatus,
    supersededByFeatureSchemaId = supersededByFeatureSchemaId,
    recommendedForDomain = recommendedForDomain,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface FeatureSchemaCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<FeatureSchemaCatalogReadModel>
    fun findAllByCriteria(criteria: FeatureSchemaCatalogReadModelCriteria?, pageable: Pageable): Page<FeatureSchemaCatalogReadModel>
    fun findById(id: UUID): FeatureSchemaCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): FeatureSchemaCatalogReadModel?
    fun findProjectionById(id: UUID): FeatureSchemaCatalogReadModelProjection?
    fun save(projection: FeatureSchemaCatalogReadModelProjection)
}

data class FeatureSchemaCatalogReadModel(
    val featureSchemaId: UUID?,
    val featureDomain: String?,
    val version: String?,
    val dataModality: String?,
    val features: List<FeatureDefinition>,
    val labels: List<LabelDefinition>,
    val featureCount: Int?,
    val schemaStatus: String?,
    val supersededByFeatureSchemaId: UUID?,
    val recommendedForDomain: Boolean?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
