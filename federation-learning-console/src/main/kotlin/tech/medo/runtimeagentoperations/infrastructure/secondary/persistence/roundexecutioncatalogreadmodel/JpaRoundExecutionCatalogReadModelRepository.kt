package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.roundexecutioncatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.UUID;

import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModel
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModelProjection
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModelRepository
import tech.medo.runtimeagentoperations.roundexecutioncatalog.toReadModel

@Repository
class JpaRoundExecutionCatalogReadModelRepository(private val jpaRepository: SpringDataRoundExecutionCatalogReadModelRepository, private val objectMapper: ObjectMapper) : RoundExecutionCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RoundExecutionCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RoundExecutionCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RoundExecutionCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RoundExecutionCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RoundExecutionCatalogReadModelEntity.toProjection(): RoundExecutionCatalogReadModelProjection =
        RoundExecutionCatalogReadModelProjection().also {
            it.roundExecutionId = this@toProjection.roundExecutionId
            it.executionSessionId = this@toProjection.executionSessionId
            it.executionPlanId = this@toProjection.executionPlanId
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.roundId = this@toProjection.roundId
            it.roundNumber = this@toProjection.roundNumber
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.state = this@toProjection.state
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.baseModelVersionId = this@toProjection.baseModelVersionId
            it.runtimeEngineJobId = this@toProjection.runtimeEngineJobId
            it.localExecutionRequirementsSatisfied = this@toProjection.localExecutionRequirementsSatisfied
            it.runtimeIdentityMatched = this@toProjection.runtimeIdentityMatched
            it.runtimeDatasetBindingAvailable = this@toProjection.runtimeDatasetBindingAvailable
            it.datasetAccessValidated = this@toProjection.datasetAccessValidated
            it.baseModelAvailable = this@toProjection.baseModelAvailable
            it.trainingConfigurationSupported = this@toProjection.trainingConfigurationSupported
            it.runtimeResourceAvailable = this@toProjection.runtimeResourceAvailable
            it.runtimeAgentIdle = this@toProjection.runtimeAgentIdle
            it.updateArtifactId = this@toProjection.updateArtifactId
            it.artifactRef = this@toProjection.artifactRef
            it.artifactDigest = this@toProjection.artifactDigest
            it.trainingLoss = this@toProjection.trainingLoss
            it.receivedAt = this@toProjection.receivedAt
            it.acceptedAt = this@toProjection.acceptedAt
            it.rejectedAt = this@toProjection.rejectedAt
            it.startedAt = this@toProjection.startedAt
            it.completedAt = this@toProjection.completedAt
            it.failedAt = this@toProjection.failedAt
            it.submittedAt = this@toProjection.submittedAt
            it.failureReason = this@toProjection.failureReason
            it.retryReason = this@toProjection.retryReason
            it.runtimeEngineReleased = this@toProjection.runtimeEngineReleased
            it.runtimeEngineReleaseFailureReason = this@toProjection.runtimeEngineReleaseFailureReason
            it.rejectionReasons = this@toProjection.rejectionReasons?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RoundExecutionCatalogReadModelProjection.toEntity(): RoundExecutionCatalogReadModelEntity =
        RoundExecutionCatalogReadModelEntity().also {
            it.roundExecutionId = this@toEntity.roundExecutionId
            it.executionSessionId = this@toEntity.executionSessionId
            it.executionPlanId = this@toEntity.executionPlanId
            it.trainingJobId = this@toEntity.trainingJobId
            it.trainingRunConfigurationId = this@toEntity.trainingRunConfigurationId
            it.roundId = this@toEntity.roundId
            it.roundNumber = this@toEntity.roundNumber
            it.organizationId = this@toEntity.organizationId
            it.runtimeId = this@toEntity.runtimeId
            it.state = this@toEntity.state
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.baseModelVersionId = this@toEntity.baseModelVersionId
            it.runtimeEngineJobId = this@toEntity.runtimeEngineJobId
            it.localExecutionRequirementsSatisfied = this@toEntity.localExecutionRequirementsSatisfied
            it.runtimeIdentityMatched = this@toEntity.runtimeIdentityMatched
            it.runtimeDatasetBindingAvailable = this@toEntity.runtimeDatasetBindingAvailable
            it.datasetAccessValidated = this@toEntity.datasetAccessValidated
            it.baseModelAvailable = this@toEntity.baseModelAvailable
            it.trainingConfigurationSupported = this@toEntity.trainingConfigurationSupported
            it.runtimeResourceAvailable = this@toEntity.runtimeResourceAvailable
            it.runtimeAgentIdle = this@toEntity.runtimeAgentIdle
            it.updateArtifactId = this@toEntity.updateArtifactId
            it.artifactRef = this@toEntity.artifactRef
            it.artifactDigest = this@toEntity.artifactDigest
            it.trainingLoss = this@toEntity.trainingLoss
            it.receivedAt = this@toEntity.receivedAt
            it.acceptedAt = this@toEntity.acceptedAt
            it.rejectedAt = this@toEntity.rejectedAt
            it.startedAt = this@toEntity.startedAt
            it.completedAt = this@toEntity.completedAt
            it.failedAt = this@toEntity.failedAt
            it.submittedAt = this@toEntity.submittedAt
            it.failureReason = this@toEntity.failureReason
            it.retryReason = this@toEntity.retryReason
            it.runtimeEngineReleased = this@toEntity.runtimeEngineReleased
            it.runtimeEngineReleaseFailureReason = this@toEntity.runtimeEngineReleaseFailureReason
            it.rejectionReasons = objectMapper.writeValueAsString(this@toEntity.rejectionReasons)
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
