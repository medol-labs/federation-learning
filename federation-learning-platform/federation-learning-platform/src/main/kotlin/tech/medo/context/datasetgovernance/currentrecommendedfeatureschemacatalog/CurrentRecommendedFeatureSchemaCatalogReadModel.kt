package tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class CurrentRecommendedFeatureSchemaCatalogReadModelQuery

class CurrentRecommendedFeatureSchemaCatalogReadModelCriteria {
    var featureDomain: StringFilter? = null
    var recommendedFeatureSchemaId: StringFilter? = null
    var recommendedVersion: StringFilter? = null
    var recommendedAt: RangeFilter<LocalDateTime>? = null
    var recommendationNote: StringFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class CurrentRecommendedFeatureSchemaCatalogReadModelProjection : MetadataProjection {
    var featureDomain: String? = null
    var recommendedFeatureSchemaId: UUID? = null
    var recommendedVersion: String? = null
    var recommendedAt: LocalDateTime? = null
    var recommendationNote: String? = null
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

fun CurrentRecommendedFeatureSchemaCatalogReadModelProjection.toReadModel(): CurrentRecommendedFeatureSchemaCatalogReadModel =
    CurrentRecommendedFeatureSchemaCatalogReadModel(
    featureDomain = featureDomain,
    recommendedFeatureSchemaId = recommendedFeatureSchemaId,
    recommendedVersion = recommendedVersion,
    recommendedAt = recommendedAt,
    recommendationNote = recommendationNote,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface CurrentRecommendedFeatureSchemaCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<CurrentRecommendedFeatureSchemaCatalogReadModel>
    fun findAllByCriteria(criteria: CurrentRecommendedFeatureSchemaCatalogReadModelCriteria?, pageable: Pageable): Page<CurrentRecommendedFeatureSchemaCatalogReadModel>
    fun findById(id: String): CurrentRecommendedFeatureSchemaCatalogReadModel?
    fun findByIdWithAccessPolicy(id: String): CurrentRecommendedFeatureSchemaCatalogReadModel?
    fun findProjectionById(id: String): CurrentRecommendedFeatureSchemaCatalogReadModelProjection?
    fun save(projection: CurrentRecommendedFeatureSchemaCatalogReadModelProjection)
}

data class CurrentRecommendedFeatureSchemaCatalogReadModel(
    val featureDomain: String?,
    val recommendedFeatureSchemaId: UUID?,
    val recommendedVersion: String?,
    val recommendedAt: LocalDateTime?,
    val recommendationNote: String?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
