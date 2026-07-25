package tech.medo.trainingorchestration.trainingjobdashboard

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum;
import java.math.BigDecimal;


class TrainingJobDashboardReadModelQuery

class TrainingJobDashboardReadModelProjection : MetadataProjection {
    var trainingJobId: UUID? = null
    var federationId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    var federationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var objective: String? = null
    var strategyName: String? = null
    var aggregationAlgorithm: String? = null
    var secureAggregationRequired: Boolean? = null
    var state: TrainingJobStateEnum? = null
    var workflowStage: String? = null
    var workflowStep: Int? = null
    var nextAction: String? = null
    var availableActions: List<String> = emptyList()
    var blockedReason: String? = null
    var canSubmit: Boolean? = null
    var canStartRound: Boolean? = null
    var canPause: Boolean? = null
    var canResume: Boolean? = null
    var canCancel: Boolean? = null
    var canComplete: Boolean? = null
    var currentRoundNumber: Int? = null
    var startedRuntimeCount: Int? = null
    var minimumNodesPerRound: Int? = null
    var maxRounds: Int? = null
    var roundProgressPercent: Int? = null
    var globalAccuracy: BigDecimal? = null
    var finalModelVersionId: UUID? = null
    var stopReason: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun TrainingJobDashboardReadModelProjection.toReadModel(): TrainingJobDashboardReadModel =
    TrainingJobDashboardReadModel(
    trainingJobId = trainingJobId,
    federationId = federationId,
    trainingRunConfigurationId = trainingRunConfigurationId,
    featureSchemaId = featureSchemaId,
    federationName = federationName,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    objective = objective,
    strategyName = strategyName,
    aggregationAlgorithm = aggregationAlgorithm,
    secureAggregationRequired = secureAggregationRequired,
    state = state,
    workflowStage = workflowStage,
    workflowStep = workflowStep,
    nextAction = nextAction,
    availableActions = availableActions,
    blockedReason = blockedReason,
    canSubmit = canSubmit,
    canStartRound = canStartRound,
    canPause = canPause,
    canResume = canResume,
    canCancel = canCancel,
    canComplete = canComplete,
    currentRoundNumber = currentRoundNumber,
    startedRuntimeCount = startedRuntimeCount,
    minimumNodesPerRound = minimumNodesPerRound,
    maxRounds = maxRounds,
    roundProgressPercent = roundProgressPercent,
    globalAccuracy = globalAccuracy,
    finalModelVersionId = finalModelVersionId,
    stopReason = stopReason,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface TrainingJobDashboardReadModelRepository {
    fun findAll(pageable: Pageable): Page<TrainingJobDashboardReadModel>
    fun findById(id: UUID): TrainingJobDashboardReadModel?
    fun findProjectionById(id: UUID): TrainingJobDashboardReadModelProjection?
    fun save(projection: TrainingJobDashboardReadModelProjection)
}

data class TrainingJobDashboardReadModel(
    val trainingJobId: UUID?,
    val federationId: UUID?,
    val trainingRunConfigurationId: UUID?,
    val featureSchemaId: UUID?,
    val federationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val objective: String?,
    val strategyName: String?,
    val aggregationAlgorithm: String?,
    val secureAggregationRequired: Boolean?,
    val state: TrainingJobStateEnum?,
    val workflowStage: String?,
    val workflowStep: Int?,
    val nextAction: String?,
    val availableActions: List<String>,
    val blockedReason: String?,
    val canSubmit: Boolean?,
    val canStartRound: Boolean?,
    val canPause: Boolean?,
    val canResume: Boolean?,
    val canCancel: Boolean?,
    val canComplete: Boolean?,
    val currentRoundNumber: Int?,
    val startedRuntimeCount: Int?,
    val minimumNodesPerRound: Int?,
    val maxRounds: Int?,
    val roundProgressPercent: Int?,
    val globalAccuracy: BigDecimal?,
    val finalModelVersionId: UUID?,
    val stopReason: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
