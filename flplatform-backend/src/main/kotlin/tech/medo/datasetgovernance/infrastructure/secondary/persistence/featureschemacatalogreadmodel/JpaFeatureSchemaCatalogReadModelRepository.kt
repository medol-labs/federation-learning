package tech.medo.datasetgovernance.infrastructure.secondary.persistence.featureschemacatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModel
import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModelProjection
import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModelRepository
import tech.medo.datasetgovernance.featureschemacatalog.toReadModel

@Repository
class JpaFeatureSchemaCatalogReadModelRepository(private val jpaRepository: SpringDataFeatureSchemaCatalogReadModelRepository) : FeatureSchemaCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<FeatureSchemaCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): FeatureSchemaCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): FeatureSchemaCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: FeatureSchemaCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun FeatureSchemaCatalogReadModelEntity.toProjection(): FeatureSchemaCatalogReadModelProjection =
        FeatureSchemaCatalogReadModelProjection().also {
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.featureDomain = this@toProjection.featureDomain
            it.version = this@toProjection.version
            it.dataModality = this@toProjection.dataModality
            it.features = this@toProjection.features
            it.labels = this@toProjection.labels
            it.featureCount = this@toProjection.featureCount
            it.schemaStatus = this@toProjection.schemaStatus
            it.supersededByFeatureSchemaId = this@toProjection.supersededByFeatureSchemaId
            it.recommendedForDomain = this@toProjection.recommendedForDomain
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun FeatureSchemaCatalogReadModelProjection.toEntity(): FeatureSchemaCatalogReadModelEntity =
        FeatureSchemaCatalogReadModelEntity().also {
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.featureDomain = this@toEntity.featureDomain
            it.version = this@toEntity.version
            it.dataModality = this@toEntity.dataModality
            it.features = this@toEntity.features
            it.labels = this@toEntity.labels
            it.featureCount = this@toEntity.featureCount
            it.schemaStatus = this@toEntity.schemaStatus
            it.supersededByFeatureSchemaId = this@toEntity.supersededByFeatureSchemaId
            it.recommendedForDomain = this@toEntity.recommendedForDomain
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
