package tech.medo.datasetgovernance.infrastructure.secondary.persistence.currentrecommendedfeatureschemacatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository


import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModel
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModelProjection
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModelRepository
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.toReadModel

@Repository
class JpaCurrentRecommendedFeatureSchemaCatalogReadModelRepository(private val jpaRepository: SpringDataCurrentRecommendedFeatureSchemaCatalogReadModelRepository) : CurrentRecommendedFeatureSchemaCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<CurrentRecommendedFeatureSchemaCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: String): CurrentRecommendedFeatureSchemaCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: String): CurrentRecommendedFeatureSchemaCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: CurrentRecommendedFeatureSchemaCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun CurrentRecommendedFeatureSchemaCatalogReadModelEntity.toProjection(): CurrentRecommendedFeatureSchemaCatalogReadModelProjection =
        CurrentRecommendedFeatureSchemaCatalogReadModelProjection().also {
            it.featureDomain = this@toProjection.featureDomain
            it.recommendedFeatureSchemaId = this@toProjection.recommendedFeatureSchemaId
            it.recommendedVersion = this@toProjection.recommendedVersion
            it.recommendedAt = this@toProjection.recommendedAt
            it.recommendationNote = this@toProjection.recommendationNote
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun CurrentRecommendedFeatureSchemaCatalogReadModelProjection.toEntity(): CurrentRecommendedFeatureSchemaCatalogReadModelEntity =
        CurrentRecommendedFeatureSchemaCatalogReadModelEntity().also {
            it.featureDomain = this@toEntity.featureDomain
            it.recommendedFeatureSchemaId = this@toEntity.recommendedFeatureSchemaId
            it.recommendedVersion = this@toEntity.recommendedVersion
            it.recommendedAt = this@toEntity.recommendedAt
            it.recommendationNote = this@toEntity.recommendationNote
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
