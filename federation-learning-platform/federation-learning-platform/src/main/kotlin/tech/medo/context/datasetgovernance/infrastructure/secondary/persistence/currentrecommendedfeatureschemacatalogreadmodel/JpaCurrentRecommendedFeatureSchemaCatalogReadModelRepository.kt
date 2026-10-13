package tech.medo.datasetgovernance.infrastructure.secondary.persistence.currentrecommendedfeatureschemacatalogreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModel
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModelCriteria
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModelProjection
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModelRepository
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.toReadModel

@Repository
class JpaCurrentRecommendedFeatureSchemaCatalogReadModelRepository(
    private val jpaRepository: SpringDataCurrentRecommendedFeatureSchemaCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: CurrentRecommendedFeatureSchemaCatalogReadModelQueryService
) : CurrentRecommendedFeatureSchemaCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<CurrentRecommendedFeatureSchemaCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: CurrentRecommendedFeatureSchemaCatalogReadModelCriteria?, pageable: Pageable): Page<CurrentRecommendedFeatureSchemaCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: String): CurrentRecommendedFeatureSchemaCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: String): CurrentRecommendedFeatureSchemaCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: String): CurrentRecommendedFeatureSchemaCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: CurrentRecommendedFeatureSchemaCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun idSpecification(id: String): Specification<CurrentRecommendedFeatureSchemaCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<String>("featureDomain"), id)
        }

    private fun accessSpecification(): Specification<CurrentRecommendedFeatureSchemaCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "DatasetGovernance",
            readModelName = "CurrentRecommendedFeatureSchemaCatalog",
            policy = ReadModelAccessPolicy(
            name = null,
            fields = emptyList<String>()
        )
        )

    private fun CurrentRecommendedFeatureSchemaCatalogReadModelEntity.toProjection(): CurrentRecommendedFeatureSchemaCatalogReadModelProjection =
        CurrentRecommendedFeatureSchemaCatalogReadModelProjection().also {
            it.featureDomain = this@toProjection.featureDomain
            it.recommendedFeatureSchemaId = this@toProjection.recommendedFeatureSchemaId
            it.recommendedVersion = this@toProjection.recommendedVersion
            it.recommendedAt = this@toProjection.recommendedAt
            it.recommendationNote = this@toProjection.recommendationNote
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
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
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
