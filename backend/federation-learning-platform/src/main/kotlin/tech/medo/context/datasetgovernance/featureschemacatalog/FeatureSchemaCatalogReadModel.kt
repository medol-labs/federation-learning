package tech.medo.datasetgovernance.featureschemacatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;

import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.IntegerFilter
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
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
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
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
