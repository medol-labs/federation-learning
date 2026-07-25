package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingroundprogressreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.UUID;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;

import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModel
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelKey
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelProjection
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelRepository
import tech.medo.trainingorchestration.trainingroundprogress.toReadModel

@Repository
class JpaTrainingRoundProgressReadModelRepository(private val jpaRepository: SpringDataTrainingRoundProgressReadModelRepository, private val objectMapper: ObjectMapper) : TrainingRoundProgressReadModelRepository {
    override fun findAll(pageable: Pageable): Page<TrainingRoundProgressReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: TrainingRoundProgressReadModelKey): TrainingRoundProgressReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: TrainingRoundProgressReadModelKey): TrainingRoundProgressReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun findProjectionsByTrainingJobId(trainingJobId: UUID): List<TrainingRoundProgressReadModelProjection> =
        jpaRepository.findAllByTrainingJobId(trainingJobId).map { it.toProjection() }

    override fun findProjectionsByRoundId(roundId: UUID): List<TrainingRoundProgressReadModelProjection> =
        jpaRepository.findAllByRoundId(roundId).map { it.toProjection() }

    override fun save(projection: TrainingRoundProgressReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun TrainingRoundProgressReadModelEntity.toProjection(): TrainingRoundProgressReadModelProjection =
        TrainingRoundProgressReadModelProjection().also {
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.roundId = this@toProjection.roundId
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.roundNumber = this@toProjection.roundNumber
            it.state = this@toProjection.state
            it.selectedOrganizationIds = this@toProjection.selectedOrganizationIds?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<UUID>>() {}) } ?: emptyList()
            it.selectedParticipants = this@toProjection.selectedParticipants?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<TrainingRoundParticipant>>() {}) } ?: emptyList()
            it.selectedOrganizationCount = this@toProjection.selectedOrganizationCount
            it.selectedRuntimeCount = this@toProjection.selectedRuntimeCount
            it.targetRuntimeCount = this@toProjection.targetRuntimeCount
            it.executionPlanDispatchedCount = this@toProjection.executionPlanDispatchedCount
            it.roundExecutionStartedCount = this@toProjection.roundExecutionStartedCount
            it.submittedModelUpdateCount = this@toProjection.submittedModelUpdateCount
            it.rejectedUpdateCount = this@toProjection.rejectedUpdateCount
            it.acceptedModelUpdateCount = this@toProjection.acceptedModelUpdateCount
            it.acceptedUpdateCount = this@toProjection.acceptedUpdateCount
            it.pendingUpdateCount = this@toProjection.pendingUpdateCount
            it.failedRoundExecutionCount = this@toProjection.failedRoundExecutionCount
            it.completedRoundExecutionCount = this@toProjection.completedRoundExecutionCount
            it.retriedRoundExecutionCount = this@toProjection.retriedRoundExecutionCount
            it.failedRoundExecutionRetryCount = this@toProjection.failedRoundExecutionRetryCount
            it.quorumMet = this@toProjection.quorumMet
            it.quorumStatus = this@toProjection.quorumStatus
            it.minimumNodesPerRound = this@toProjection.minimumNodesPerRound
            it.aggregationReady = this@toProjection.aggregationReady
            it.secureAggregationRequired = this@toProjection.secureAggregationRequired
            it.secureAggregationStatus = this@toProjection.secureAggregationStatus
            it.evaluationComplete = this@toProjection.evaluationComplete
            it.progressPercent = this@toProjection.progressPercent
            it.currentPhase = this@toProjection.currentPhase
            it.nextAction = this@toProjection.nextAction
            it.blockedReason = this@toProjection.blockedReason
            it.delayedReason = this@toProjection.delayedReason
            it.roundStartedAt = this@toProjection.roundStartedAt
            it.contributionDeadlineAt = this@toProjection.contributionDeadlineAt
            it.aggregationStartedAt = this@toProjection.aggregationStartedAt
            it.evaluationSubmittedAt = this@toProjection.evaluationSubmittedAt
            it.completedAt = this@toProjection.completedAt
            it.failedAt = this@toProjection.failedAt
            it.baseModelVersionId = this@toProjection.baseModelVersionId
            it.artifactRefs = this@toProjection.artifactRefs?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.rejectedUpdateReasons = this@toProjection.rejectedUpdateReasons?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.aggregatedModelVersionId = this@toProjection.aggregatedModelVersionId
            it.globalAccuracy = this@toProjection.globalAccuracy
            it.globalFairnessScore = this@toProjection.globalFairnessScore
            it.failureReason = this@toProjection.failureReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun TrainingRoundProgressReadModelProjection.toEntity(): TrainingRoundProgressReadModelEntity =
        TrainingRoundProgressReadModelEntity().also {
            it.trainingJobId = this@toEntity.trainingJobId
            it.trainingRunConfigurationId = this@toEntity.trainingRunConfigurationId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.roundId = this@toEntity.roundId
            it.trainingJobObjective = this@toEntity.trainingJobObjective
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.roundNumber = this@toEntity.roundNumber
            it.state = this@toEntity.state
            it.selectedOrganizationIds = objectMapper.writeValueAsString(this@toEntity.selectedOrganizationIds)
            it.selectedParticipants = objectMapper.writeValueAsString(this@toEntity.selectedParticipants)
            it.selectedOrganizationCount = this@toEntity.selectedOrganizationCount
            it.selectedRuntimeCount = this@toEntity.selectedRuntimeCount
            it.targetRuntimeCount = this@toEntity.targetRuntimeCount
            it.executionPlanDispatchedCount = this@toEntity.executionPlanDispatchedCount
            it.roundExecutionStartedCount = this@toEntity.roundExecutionStartedCount
            it.submittedModelUpdateCount = this@toEntity.submittedModelUpdateCount
            it.rejectedUpdateCount = this@toEntity.rejectedUpdateCount
            it.acceptedModelUpdateCount = this@toEntity.acceptedModelUpdateCount
            it.acceptedUpdateCount = this@toEntity.acceptedUpdateCount
            it.pendingUpdateCount = this@toEntity.pendingUpdateCount
            it.failedRoundExecutionCount = this@toEntity.failedRoundExecutionCount
            it.completedRoundExecutionCount = this@toEntity.completedRoundExecutionCount
            it.retriedRoundExecutionCount = this@toEntity.retriedRoundExecutionCount
            it.failedRoundExecutionRetryCount = this@toEntity.failedRoundExecutionRetryCount
            it.quorumMet = this@toEntity.quorumMet
            it.quorumStatus = this@toEntity.quorumStatus
            it.minimumNodesPerRound = this@toEntity.minimumNodesPerRound
            it.aggregationReady = this@toEntity.aggregationReady
            it.secureAggregationRequired = this@toEntity.secureAggregationRequired
            it.secureAggregationStatus = this@toEntity.secureAggregationStatus
            it.evaluationComplete = this@toEntity.evaluationComplete
            it.progressPercent = this@toEntity.progressPercent
            it.currentPhase = this@toEntity.currentPhase
            it.nextAction = this@toEntity.nextAction
            it.blockedReason = this@toEntity.blockedReason
            it.delayedReason = this@toEntity.delayedReason
            it.roundStartedAt = this@toEntity.roundStartedAt
            it.contributionDeadlineAt = this@toEntity.contributionDeadlineAt
            it.aggregationStartedAt = this@toEntity.aggregationStartedAt
            it.evaluationSubmittedAt = this@toEntity.evaluationSubmittedAt
            it.completedAt = this@toEntity.completedAt
            it.failedAt = this@toEntity.failedAt
            it.baseModelVersionId = this@toEntity.baseModelVersionId
            it.artifactRefs = objectMapper.writeValueAsString(this@toEntity.artifactRefs)
            it.rejectedUpdateReasons = objectMapper.writeValueAsString(this@toEntity.rejectedUpdateReasons)
            it.aggregatedModelVersionId = this@toEntity.aggregatedModelVersionId
            it.globalAccuracy = this@toEntity.globalAccuracy
            it.globalFairnessScore = this@toEntity.globalFairnessScore
            it.failureReason = this@toEntity.failureReason
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
