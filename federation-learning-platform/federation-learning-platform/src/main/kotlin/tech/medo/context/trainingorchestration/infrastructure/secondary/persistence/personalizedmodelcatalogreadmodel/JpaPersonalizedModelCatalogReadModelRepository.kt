package tech.medo.trainingorchestration.infrastructure.secondary.persistence.personalizedmodelcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.states.PersonalizedModelResultStateEnum;

import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModel
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelCriteria
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelProjection
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelRepository
import tech.medo.trainingorchestration.personalizedmodelcatalog.toReadModel

@Repository
class JpaPersonalizedModelCatalogReadModelRepository(
    private val jpaRepository: SpringDataPersonalizedModelCatalogReadModelRepository,
    private val queryService: PersonalizedModelCatalogReadModelQueryService
) : PersonalizedModelCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<PersonalizedModelCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: PersonalizedModelCatalogReadModelCriteria?, pageable: Pageable): Page<PersonalizedModelCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): PersonalizedModelCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): PersonalizedModelCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: PersonalizedModelCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun PersonalizedModelCatalogReadModelEntity.toProjection(): PersonalizedModelCatalogReadModelProjection =
        PersonalizedModelCatalogReadModelProjection().also {
            it.personalizedModelId = this@toProjection.personalizedModelId
            it.modelUpdateSubmissionId = this@toProjection.modelUpdateSubmissionId
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.roundId = this@toProjection.roundId
            it.roundNumber = this@toProjection.roundNumber
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.personalizationMode = this@toProjection.personalizationMode
            it.personalizationScope = this@toProjection.personalizationScope
            it.personalizedArtifactPolicy = this@toProjection.personalizedArtifactPolicy
            it.personalizationMetric = this@toProjection.personalizationMetric
            it.minimumPersonalizationLift = this@toProjection.minimumPersonalizationLift
            it.personalizationTargetType = this@toProjection.personalizationTargetType
            it.personalizationTargetId = this@toProjection.personalizationTargetId
            it.personalizationCohortId = this@toProjection.personalizationCohortId
            it.personalizationProfileId = this@toProjection.personalizationProfileId
            it.personalizedModelArtifactRef = this@toProjection.personalizedModelArtifactRef
            it.personalizedModelArtifactDigest = this@toProjection.personalizedModelArtifactDigest
            it.personalizedMetricsArtifactRef = this@toProjection.personalizedMetricsArtifactRef
            it.personalizedAccuracy = this@toProjection.personalizedAccuracy
            it.personalizationLift = this@toProjection.personalizationLift
            it.state = this@toProjection.state
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun PersonalizedModelCatalogReadModelProjection.toEntity(): PersonalizedModelCatalogReadModelEntity =
        PersonalizedModelCatalogReadModelEntity().also {
            it.personalizedModelId = this@toEntity.personalizedModelId
            it.modelUpdateSubmissionId = this@toEntity.modelUpdateSubmissionId
            it.trainingJobId = this@toEntity.trainingJobId
            it.trainingRunConfigurationId = this@toEntity.trainingRunConfigurationId
            it.roundId = this@toEntity.roundId
            it.roundNumber = this@toEntity.roundNumber
            it.runtimeId = this@toEntity.runtimeId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.personalizationMode = this@toEntity.personalizationMode
            it.personalizationScope = this@toEntity.personalizationScope
            it.personalizedArtifactPolicy = this@toEntity.personalizedArtifactPolicy
            it.personalizationMetric = this@toEntity.personalizationMetric
            it.minimumPersonalizationLift = this@toEntity.minimumPersonalizationLift
            it.personalizationTargetType = this@toEntity.personalizationTargetType
            it.personalizationTargetId = this@toEntity.personalizationTargetId
            it.personalizationCohortId = this@toEntity.personalizationCohortId
            it.personalizationProfileId = this@toEntity.personalizationProfileId
            it.personalizedModelArtifactRef = this@toEntity.personalizedModelArtifactRef
            it.personalizedModelArtifactDigest = this@toEntity.personalizedModelArtifactDigest
            it.personalizedMetricsArtifactRef = this@toEntity.personalizedMetricsArtifactRef
            it.personalizedAccuracy = this@toEntity.personalizedAccuracy
            it.personalizationLift = this@toEntity.personalizationLift
            it.state = this@toEntity.state
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
