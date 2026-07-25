package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingjobdashboardreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.UUID;

import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModel
import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModelProjection
import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModelRepository
import tech.medo.trainingorchestration.trainingjobdashboard.toReadModel

@Repository
class JpaTrainingJobDashboardReadModelRepository(private val jpaRepository: SpringDataTrainingJobDashboardReadModelRepository, private val objectMapper: ObjectMapper) : TrainingJobDashboardReadModelRepository {
    override fun findAll(pageable: Pageable): Page<TrainingJobDashboardReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): TrainingJobDashboardReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): TrainingJobDashboardReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: TrainingJobDashboardReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun TrainingJobDashboardReadModelEntity.toProjection(): TrainingJobDashboardReadModelProjection =
        TrainingJobDashboardReadModelProjection().also {
            it.trainingJobId = this@toProjection.trainingJobId
            it.federationId = this@toProjection.federationId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.federationName = this@toProjection.federationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.objective = this@toProjection.objective
            it.strategyName = this@toProjection.strategyName
            it.aggregationAlgorithm = this@toProjection.aggregationAlgorithm
            it.secureAggregationRequired = this@toProjection.secureAggregationRequired
            it.state = this@toProjection.state
            it.workflowStage = this@toProjection.workflowStage
            it.workflowStep = this@toProjection.workflowStep
            it.nextAction = this@toProjection.nextAction
            it.availableActions = this@toProjection.availableActions?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.blockedReason = this@toProjection.blockedReason
            it.canSubmit = this@toProjection.canSubmit
            it.canStartRound = this@toProjection.canStartRound
            it.canPause = this@toProjection.canPause
            it.canResume = this@toProjection.canResume
            it.canCancel = this@toProjection.canCancel
            it.canComplete = this@toProjection.canComplete
            it.currentRoundNumber = this@toProjection.currentRoundNumber
            it.startedRuntimeCount = this@toProjection.startedRuntimeCount
            it.minimumNodesPerRound = this@toProjection.minimumNodesPerRound
            it.maxRounds = this@toProjection.maxRounds
            it.roundProgressPercent = this@toProjection.roundProgressPercent
            it.globalAccuracy = this@toProjection.globalAccuracy
            it.finalModelVersionId = this@toProjection.finalModelVersionId
            it.stopReason = this@toProjection.stopReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun TrainingJobDashboardReadModelProjection.toEntity(): TrainingJobDashboardReadModelEntity =
        TrainingJobDashboardReadModelEntity().also {
            it.trainingJobId = this@toEntity.trainingJobId
            it.federationId = this@toEntity.federationId
            it.trainingRunConfigurationId = this@toEntity.trainingRunConfigurationId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.federationName = this@toEntity.federationName
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.objective = this@toEntity.objective
            it.strategyName = this@toEntity.strategyName
            it.aggregationAlgorithm = this@toEntity.aggregationAlgorithm
            it.secureAggregationRequired = this@toEntity.secureAggregationRequired
            it.state = this@toEntity.state
            it.workflowStage = this@toEntity.workflowStage
            it.workflowStep = this@toEntity.workflowStep
            it.nextAction = this@toEntity.nextAction
            it.availableActions = objectMapper.writeValueAsString(this@toEntity.availableActions)
            it.blockedReason = this@toEntity.blockedReason
            it.canSubmit = this@toEntity.canSubmit
            it.canStartRound = this@toEntity.canStartRound
            it.canPause = this@toEntity.canPause
            it.canResume = this@toEntity.canResume
            it.canCancel = this@toEntity.canCancel
            it.canComplete = this@toEntity.canComplete
            it.currentRoundNumber = this@toEntity.currentRoundNumber
            it.startedRuntimeCount = this@toEntity.startedRuntimeCount
            it.minimumNodesPerRound = this@toEntity.minimumNodesPerRound
            it.maxRounds = this@toEntity.maxRounds
            it.roundProgressPercent = this@toEntity.roundProgressPercent
            it.globalAccuracy = this@toEntity.globalAccuracy
            it.finalModelVersionId = this@toEntity.finalModelVersionId
            it.stopReason = this@toEntity.stopReason
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
