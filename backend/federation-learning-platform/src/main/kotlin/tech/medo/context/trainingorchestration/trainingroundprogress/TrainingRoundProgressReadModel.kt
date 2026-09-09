package tech.medo.trainingorchestration.trainingroundprogress

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


data class TrainingRoundProgressReadModelKey(
    var trainingJobId: UUID? = null,
    var roundId: UUID? = null
) : java.io.Serializable

class TrainingRoundProgressReadModelQuery

class TrainingRoundProgressReadModelCriteria {
    var trainingJobId: StringFilter? = null
    var trainingRunConfigurationId: StringFilter? = null
    var featureSchemaId: StringFilter? = null
    var roundId: StringFilter? = null
    var trainingJobObjective: StringFilter? = null
    var featureDomain: StringFilter? = null
    var featureSchemaVersion: StringFilter? = null
    var roundNumber: IntegerFilter? = null
    var state: Filter<TrainingRoundStateEnum>? = null
    var selectedOrganizationCount: IntegerFilter? = null
    var selectedRuntimeCount: IntegerFilter? = null
    var targetRuntimeCount: IntegerFilter? = null
    var executionPlanDispatchedCount: IntegerFilter? = null
    var roundExecutionStartedCount: IntegerFilter? = null
    var submittedModelUpdateCount: IntegerFilter? = null
    var rejectedUpdateCount: IntegerFilter? = null
    var acceptedModelUpdateCount: IntegerFilter? = null
    var acceptedUpdateCount: IntegerFilter? = null
    var pendingUpdateCount: IntegerFilter? = null
    var failedRoundExecutionCount: IntegerFilter? = null
    var completedRoundExecutionCount: IntegerFilter? = null
    var retriedRoundExecutionCount: IntegerFilter? = null
    var failedRoundExecutionRetryCount: IntegerFilter? = null
    var quorumMet: BooleanFilter? = null
    var quorumStatus: StringFilter? = null
    var minimumNodesPerRound: IntegerFilter? = null
    var aggregationReady: BooleanFilter? = null
    var secureAggregationRequired: BooleanFilter? = null
    var secureAggregationStatus: StringFilter? = null
    var evaluationComplete: BooleanFilter? = null
    var progressPercent: IntegerFilter? = null
    var currentPhase: StringFilter? = null
    var nextAction: StringFilter? = null
    var blockedReason: StringFilter? = null
    var delayedReason: StringFilter? = null
    var roundStartedAt: RangeFilter<LocalDateTime>? = null
    var contributionDeadlineAt: RangeFilter<LocalDateTime>? = null
    var aggregationStartedAt: RangeFilter<LocalDateTime>? = null
    var evaluationSubmittedAt: RangeFilter<LocalDateTime>? = null
    var completedAt: RangeFilter<LocalDateTime>? = null
    var failedAt: RangeFilter<LocalDateTime>? = null
    var baseModelId: StringFilter? = null
    var aggregatedModelId: StringFilter? = null
    var globalAccuracy: BigDecimalFilter? = null
    var globalFairnessScore: BigDecimalFilter? = null
    var failureReason: StringFilter? = null
}


class TrainingRoundProgressReadModelProjection : MetadataProjection {
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    var roundId: UUID? = null
    var trainingJobObjective: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var roundNumber: Int? = null
    var state: TrainingRoundStateEnum? = null
    var selectedOrganizationIds: List<UUID> = emptyList()
    var selectedParticipants: List<TrainingRoundParticipant> = emptyList()
    var selectedOrganizationCount: Int? = null
    var selectedRuntimeCount: Int? = null
    var targetRuntimeCount: Int? = null
    var executionPlanDispatchedCount: Int? = null
    var roundExecutionStartedCount: Int? = null
    var submittedModelUpdateCount: Int? = null
    var rejectedUpdateCount: Int? = null
    var acceptedModelUpdateCount: Int? = null
    var acceptedUpdateCount: Int? = null
    var pendingUpdateCount: Int? = null
    var failedRoundExecutionCount: Int? = null
    var completedRoundExecutionCount: Int? = null
    var retriedRoundExecutionCount: Int? = null
    var failedRoundExecutionRetryCount: Int? = null
    var quorumMet: Boolean? = null
    var quorumStatus: String? = null
    var minimumNodesPerRound: Int? = null
    var aggregationReady: Boolean? = null
    var secureAggregationRequired: Boolean? = null
    var secureAggregationStatus: String? = null
    var evaluationComplete: Boolean? = null
    var progressPercent: Int? = null
    var currentPhase: String? = null
    var nextAction: String? = null
    var blockedReason: String? = null
    var delayedReason: String? = null
    var roundStartedAt: LocalDateTime? = null
    var contributionDeadlineAt: LocalDateTime? = null
    var aggregationStartedAt: LocalDateTime? = null
    var evaluationSubmittedAt: LocalDateTime? = null
    var completedAt: LocalDateTime? = null
    var failedAt: LocalDateTime? = null
    var baseModelId: UUID? = null
    var artifactRefs: List<String> = emptyList()
    var rejectedUpdateReasons: List<String> = emptyList()
    var aggregatedModelId: UUID? = null
    var globalAccuracy: BigDecimal? = null
    var globalFairnessScore: BigDecimal? = null
    var failureReason: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun TrainingRoundProgressReadModelProjection.toReadModel(): TrainingRoundProgressReadModel =
    TrainingRoundProgressReadModel(
    trainingJobId = trainingJobId,
    trainingRunConfigurationId = trainingRunConfigurationId,
    featureSchemaId = featureSchemaId,
    roundId = roundId,
    trainingJobObjective = trainingJobObjective,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    roundNumber = roundNumber,
    state = state,
    selectedOrganizationIds = selectedOrganizationIds,
    selectedParticipants = selectedParticipants,
    selectedOrganizationCount = selectedOrganizationCount,
    selectedRuntimeCount = selectedRuntimeCount,
    targetRuntimeCount = targetRuntimeCount,
    executionPlanDispatchedCount = executionPlanDispatchedCount,
    roundExecutionStartedCount = roundExecutionStartedCount,
    submittedModelUpdateCount = submittedModelUpdateCount,
    rejectedUpdateCount = rejectedUpdateCount,
    acceptedModelUpdateCount = acceptedModelUpdateCount,
    acceptedUpdateCount = acceptedUpdateCount,
    pendingUpdateCount = pendingUpdateCount,
    failedRoundExecutionCount = failedRoundExecutionCount,
    completedRoundExecutionCount = completedRoundExecutionCount,
    retriedRoundExecutionCount = retriedRoundExecutionCount,
    failedRoundExecutionRetryCount = failedRoundExecutionRetryCount,
    quorumMet = quorumMet,
    quorumStatus = quorumStatus,
    minimumNodesPerRound = minimumNodesPerRound,
    aggregationReady = aggregationReady,
    secureAggregationRequired = secureAggregationRequired,
    secureAggregationStatus = secureAggregationStatus,
    evaluationComplete = evaluationComplete,
    progressPercent = progressPercent,
    currentPhase = currentPhase,
    nextAction = nextAction,
    blockedReason = blockedReason,
    delayedReason = delayedReason,
    roundStartedAt = roundStartedAt,
    contributionDeadlineAt = contributionDeadlineAt,
    aggregationStartedAt = aggregationStartedAt,
    evaluationSubmittedAt = evaluationSubmittedAt,
    completedAt = completedAt,
    failedAt = failedAt,
    baseModelId = baseModelId,
    artifactRefs = artifactRefs,
    rejectedUpdateReasons = rejectedUpdateReasons,
    aggregatedModelId = aggregatedModelId,
    globalAccuracy = globalAccuracy,
    globalFairnessScore = globalFairnessScore,
    failureReason = failureReason,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface TrainingRoundProgressReadModelRepository {
    fun findAll(pageable: Pageable): Page<TrainingRoundProgressReadModel>
    fun findAllByCriteria(criteria: TrainingRoundProgressReadModelCriteria?, pageable: Pageable): Page<TrainingRoundProgressReadModel>
    fun findById(id: TrainingRoundProgressReadModelKey): TrainingRoundProgressReadModel?
    fun findProjectionById(id: TrainingRoundProgressReadModelKey): TrainingRoundProgressReadModelProjection?
    fun findProjectionsByTrainingJobId(trainingJobId: UUID): List<TrainingRoundProgressReadModelProjection>
    fun findProjectionsByRoundId(roundId: UUID): List<TrainingRoundProgressReadModelProjection>
    fun save(projection: TrainingRoundProgressReadModelProjection)
}

data class TrainingRoundProgressReadModel(
    val trainingJobId: UUID?,
    val trainingRunConfigurationId: UUID?,
    val featureSchemaId: UUID?,
    val roundId: UUID?,
    val trainingJobObjective: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val roundNumber: Int?,
    val state: TrainingRoundStateEnum?,
    val selectedOrganizationIds: List<UUID>,
    val selectedParticipants: List<TrainingRoundParticipant>,
    val selectedOrganizationCount: Int?,
    val selectedRuntimeCount: Int?,
    val targetRuntimeCount: Int?,
    val executionPlanDispatchedCount: Int?,
    val roundExecutionStartedCount: Int?,
    val submittedModelUpdateCount: Int?,
    val rejectedUpdateCount: Int?,
    val acceptedModelUpdateCount: Int?,
    val acceptedUpdateCount: Int?,
    val pendingUpdateCount: Int?,
    val failedRoundExecutionCount: Int?,
    val completedRoundExecutionCount: Int?,
    val retriedRoundExecutionCount: Int?,
    val failedRoundExecutionRetryCount: Int?,
    val quorumMet: Boolean?,
    val quorumStatus: String?,
    val minimumNodesPerRound: Int?,
    val aggregationReady: Boolean?,
    val secureAggregationRequired: Boolean?,
    val secureAggregationStatus: String?,
    val evaluationComplete: Boolean?,
    val progressPercent: Int?,
    val currentPhase: String?,
    val nextAction: String?,
    val blockedReason: String?,
    val delayedReason: String?,
    val roundStartedAt: LocalDateTime?,
    val contributionDeadlineAt: LocalDateTime?,
    val aggregationStartedAt: LocalDateTime?,
    val evaluationSubmittedAt: LocalDateTime?,
    val completedAt: LocalDateTime?,
    val failedAt: LocalDateTime?,
    val baseModelId: UUID?,
    val artifactRefs: List<String>,
    val rejectedUpdateReasons: List<String>,
    val aggregatedModelId: UUID?,
    val globalAccuracy: BigDecimal?,
    val globalFairnessScore: BigDecimal?,
    val failureReason: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
