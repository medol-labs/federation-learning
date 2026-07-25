package tech.medo.runtimeagentoperations.roundexecution

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.events.ExecutionPlanRejectedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartFailedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionCompletedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionFailedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartRetryStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartRetryFailedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryFailedEvent
import tech.medo.runtimeagentoperations.events.AgentLocalModelUpdateSubmittedEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleasedEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = RoundExecutionTags.EXECUTION_PLAN_ID)
class RoundExecutionState @EntityCreator constructor() {

    var currentState: RoundExecutionStateEnum? = null
    private var executionPlanId: UUID? = null
    private var executionSessionId: UUID? = null
    private var trainingJobId: UUID? = null
    private var trainingRunConfigurationId: UUID? = null
    private var featureSchemaId: UUID? = null
    private var roundId: UUID? = null
    private var roundNumber: Int? = null
    private var runtimeId: UUID? = null
    private var organizationId: UUID? = null
    private var baseModelVersionId: UUID? = null
    private var localExecutionRequirementsSatisfied: Boolean? = null
    private var runtimeIdentityMatched: Boolean? = null
    private var runtimeDatasetBindingAvailable: Boolean? = null
    private var datasetAccessValidated: Boolean? = null
    private var baseModelAvailable: Boolean? = null
    private var trainingConfigurationSupported: Boolean? = null
    private var runtimeResourceAvailable: Boolean? = null
    private var runtimeAgentIdle: Boolean? = null
    private var rejectionReasons: List<String> = emptyList()
    private var roundExecutionId: UUID? = null
    private var runtimeEngineJobId: String? = null
    private var failureReason: String? = null
    private var retryReason: String? = null
    private var modelUpdateSubmissionId: UUID? = null
    private var localModelVersionId: UUID? = null
    private var updateArtifactId: UUID? = null
    private var artifactRef: String? = null
    private var artifactDigest: String? = null
    private var trainingLoss: BigDecimal? = null

    @EventSourcingHandler
    fun evolve(event: ExecutionPlanReceivedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.PLAN_RECEIVED
        executionPlanId = event.executionPlanId
        executionSessionId = event.executionSessionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        baseModelVersionId = event.baseModelVersionId
    }

    @EventSourcingHandler
    fun evolve(event: ExecutionPlanAcceptedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.PLAN_ACCEPTED
        executionPlanId = event.executionPlanId
        executionSessionId = event.executionSessionId
        trainingJobId = event.trainingJobId
        runtimeId = event.runtimeId
        localExecutionRequirementsSatisfied = event.localExecutionRequirementsSatisfied
        runtimeIdentityMatched = event.runtimeIdentityMatched
        runtimeDatasetBindingAvailable = event.runtimeDatasetBindingAvailable
        datasetAccessValidated = event.datasetAccessValidated
        baseModelAvailable = event.baseModelAvailable
        trainingConfigurationSupported = event.trainingConfigurationSupported
        runtimeResourceAvailable = event.runtimeResourceAvailable
        runtimeAgentIdle = event.runtimeAgentIdle
    }

    @EventSourcingHandler
    fun evolve(event: ExecutionPlanRejectedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.PLAN_REJECTED
        executionPlanId = event.executionPlanId
        executionSessionId = event.executionSessionId
        trainingJobId = event.trainingJobId
        runtimeId = event.runtimeId
        localExecutionRequirementsSatisfied = event.localExecutionRequirementsSatisfied
        runtimeIdentityMatched = event.runtimeIdentityMatched
        runtimeDatasetBindingAvailable = event.runtimeDatasetBindingAvailable
        datasetAccessValidated = event.datasetAccessValidated
        baseModelAvailable = event.baseModelAvailable
        trainingConfigurationSupported = event.trainingConfigurationSupported
        runtimeResourceAvailable = event.runtimeResourceAvailable
        runtimeAgentIdle = event.runtimeAgentIdle
        rejectionReasons = event.rejectionReasons
    }

    @EventSourcingHandler
    fun evolve(event: RoundExecutionStartedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RUNNING
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        baseModelVersionId = event.baseModelVersionId
        runtimeEngineJobId = event.runtimeEngineJobId
    }

    @EventSourcingHandler
    fun evolve(event: RoundExecutionStartFailedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.START_FAILED
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        baseModelVersionId = event.baseModelVersionId
        runtimeEngineJobId = event.runtimeEngineJobId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RoundExecutionCompletedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.COMPLETED
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        runtimeId = event.runtimeId
        runtimeEngineJobId = event.runtimeEngineJobId
    }

    @EventSourcingHandler
    fun evolve(event: RoundExecutionFailedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.FAILED
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        runtimeId = event.runtimeId
        runtimeEngineJobId = event.runtimeEngineJobId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RoundExecutionStartRetryStartedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RETRIED
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        baseModelVersionId = event.baseModelVersionId
        runtimeEngineJobId = event.runtimeEngineJobId
        retryReason = event.retryReason
    }

    @EventSourcingHandler
    fun evolve(event: RoundExecutionStartRetryFailedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.FAILED
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        baseModelVersionId = event.baseModelVersionId
        runtimeEngineJobId = event.runtimeEngineJobId
        retryReason = event.retryReason
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RoundExecutionRuntimeRetryStartedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RETRIED
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        baseModelVersionId = event.baseModelVersionId
        runtimeEngineJobId = event.runtimeEngineJobId
        retryReason = event.retryReason
    }

    @EventSourcingHandler
    fun evolve(event: RoundExecutionRuntimeRetryFailedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.FAILED
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        baseModelVersionId = event.baseModelVersionId
        runtimeEngineJobId = event.runtimeEngineJobId
        retryReason = event.retryReason
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: AgentLocalModelUpdateSubmittedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.UPDATE_SUBMITTED
        modelUpdateSubmissionId = event.modelUpdateSubmissionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        roundExecutionId = event.roundExecutionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
        localModelVersionId = event.localModelVersionId
        updateArtifactId = event.updateArtifactId
        artifactRef = event.artifactRef
        artifactDigest = event.artifactDigest
        trainingLoss = event.trainingLoss
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeEngineJobReleasedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASED
        roundExecutionId = event.roundExecutionId
        runtimeEngineJobId = event.runtimeEngineJobId
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeEngineJobReleaseFailedOrSkippedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASE_HANDLED
        roundExecutionId = event.roundExecutionId
        runtimeEngineJobId = event.runtimeEngineJobId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASE_HANDLED
        roundExecutionId = event.roundExecutionId
        runtimeEngineJobId = event.runtimeEngineJobId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASE_HANDLED
        roundExecutionId = event.roundExecutionId
        runtimeEngineJobId = event.runtimeEngineJobId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASE_HANDLED
        roundExecutionId = event.roundExecutionId
        runtimeEngineJobId = event.runtimeEngineJobId
        failureReason = event.failureReason
    }
}
