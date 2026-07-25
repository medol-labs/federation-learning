package tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class CurrentRecommendedFeatureSchemaCatalogReadModelQuery

class CurrentRecommendedFeatureSchemaCatalogReadModelProjection : MetadataProjection {
    var featureDomain: String? = null
    var recommendedFeatureSchemaId: UUID? = null
    var recommendedVersion: String? = null
    var recommendedAt: LocalDateTime? = null
    var recommendationNote: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun CurrentRecommendedFeatureSchemaCatalogReadModelProjection.toReadModel(): CurrentRecommendedFeatureSchemaCatalogReadModel =
    CurrentRecommendedFeatureSchemaCatalogReadModel(
    featureDomain = featureDomain,
    recommendedFeatureSchemaId = recommendedFeatureSchemaId,
    recommendedVersion = recommendedVersion,
    recommendedAt = recommendedAt,
    recommendationNote = recommendationNote,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface CurrentRecommendedFeatureSchemaCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<CurrentRecommendedFeatureSchemaCatalogReadModel>
    fun findById(id: String): CurrentRecommendedFeatureSchemaCatalogReadModel?
    fun findProjectionById(id: String): CurrentRecommendedFeatureSchemaCatalogReadModelProjection?
    fun save(projection: CurrentRecommendedFeatureSchemaCatalogReadModelProjection)
}

data class CurrentRecommendedFeatureSchemaCatalogReadModel(
    val featureDomain: String?,
    val recommendedFeatureSchemaId: UUID?,
    val recommendedVersion: String?,
    val recommendedAt: LocalDateTime?,
    val recommendationNote: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
