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
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobObservedEvent
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
    var executionPlanId: UUID? = null
    var roundExecutionId: UUID? = null
    var executionSessionId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    var roundId: UUID? = null
    var roundNumber: Int? = null
    var runtimeId: UUID? = null
    var organizationId: UUID? = null
    var baseModelId: UUID? = null
    var baseModelArtifactUri: String? = null
    var baseModelRegistryRef: String? = null
    var baseModelFormat: String? = null
    var baseModelArtifactDigest: String? = null
    var baseModelSignatureUri: String? = null
    var secureAggregationRequired: Boolean? = null
    var secureAggregationSessionId: UUID? = null
    var encryptionScheme: String? = null
    var publicKeyVersion: String? = null
    var publicKeyRef: String? = null
    var encryptedParameterScale: Int? = null
    var localExecutionRequirementsSatisfied: Boolean? = null
    var runtimeIdentityMatched: Boolean? = null
    var runtimeDatasetBindingAvailable: Boolean? = null
    var datasetAccessValidated: Boolean? = null
    var baseModelAvailable: Boolean? = null
    var trainingConfigurationSupported: Boolean? = null
    var runtimeResourceAvailable: Boolean? = null
    var runtimeAgentIdle: Boolean? = null
    var rejectionReasons: List<String> = emptyList()
    var runtimeEngineJobId: String? = null
    var failureReason: String? = null
    var observedStatus: String? = null
    var localUpdateArtifactRef: String? = null
    var encryptedUpdateArtifactRef: String? = null
    var encryptedUpdateDigest: String? = null
    var modelUpdateArtifactRef: String? = null
    var modelUpdateArtifactDigest: String? = null
    var updateProtectionType: String? = null
    var metricsArtifactRef: String? = null
    var trainingLoss: BigDecimal? = null
    var retryReason: String? = null
    var modelUpdateSubmissionId: UUID? = null
    var localModelId: UUID? = null
    var updateArtifactId: UUID? = null
    var artifactRef: String? = null
    var artifactDigest: String? = null

    @EventSourcingHandler
    fun evolve(event: ExecutionPlanReceivedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.PLAN_RECEIVED
        executionPlanId = event.executionPlanId
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        baseModelId = event.baseModelId
        baseModelArtifactUri = event.baseModelArtifactUri
        baseModelRegistryRef = event.baseModelRegistryRef
        baseModelFormat = event.baseModelFormat
        baseModelArtifactDigest = event.baseModelArtifactDigest
        baseModelSignatureUri = event.baseModelSignatureUri
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        publicKeyRef = event.publicKeyRef
        encryptedParameterScale = event.encryptedParameterScale
    }

    @EventSourcingHandler
    fun evolve(event: ExecutionPlanAcceptedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.PLAN_ACCEPTED
        executionPlanId = event.executionPlanId
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        baseModelId = event.baseModelId
        baseModelArtifactUri = event.baseModelArtifactUri
        baseModelRegistryRef = event.baseModelRegistryRef
        baseModelFormat = event.baseModelFormat
        baseModelArtifactDigest = event.baseModelArtifactDigest
        baseModelSignatureUri = event.baseModelSignatureUri
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        publicKeyRef = event.publicKeyRef
        encryptedParameterScale = event.encryptedParameterScale
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
        roundExecutionId = event.roundExecutionId
        executionSessionId = event.executionSessionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        baseModelId = event.baseModelId
        baseModelArtifactUri = event.baseModelArtifactUri
        baseModelRegistryRef = event.baseModelRegistryRef
        baseModelFormat = event.baseModelFormat
        baseModelArtifactDigest = event.baseModelArtifactDigest
        baseModelSignatureUri = event.baseModelSignatureUri
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        publicKeyRef = event.publicKeyRef
        encryptedParameterScale = event.encryptedParameterScale
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
        baseModelId = event.baseModelId
        baseModelArtifactUri = event.baseModelArtifactUri
        baseModelRegistryRef = event.baseModelRegistryRef
        baseModelFormat = event.baseModelFormat
        baseModelArtifactDigest = event.baseModelArtifactDigest
        baseModelSignatureUri = event.baseModelSignatureUri
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        publicKeyRef = event.publicKeyRef
        encryptedParameterScale = event.encryptedParameterScale
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
        baseModelId = event.baseModelId
        baseModelArtifactUri = event.baseModelArtifactUri
        baseModelRegistryRef = event.baseModelRegistryRef
        baseModelFormat = event.baseModelFormat
        baseModelArtifactDigest = event.baseModelArtifactDigest
        baseModelSignatureUri = event.baseModelSignatureUri
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        publicKeyRef = event.publicKeyRef
        encryptedParameterScale = event.encryptedParameterScale
        runtimeEngineJobId = event.runtimeEngineJobId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeEngineJobObservedEvent): RoundExecutionState = apply {
        currentState = RoundExecutionStateEnum.RUNTIME_ENGINE_RELEASED
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
        runtimeEngineJobId = event.runtimeEngineJobId
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        observedStatus = event.observedStatus
        failureReason = event.failureReason
        localUpdateArtifactRef = event.localUpdateArtifactRef
        encryptedUpdateArtifactRef = event.encryptedUpdateArtifactRef
        encryptedUpdateDigest = event.encryptedUpdateDigest
        modelUpdateArtifactRef = event.modelUpdateArtifactRef
        modelUpdateArtifactDigest = event.modelUpdateArtifactDigest
        updateProtectionType = event.updateProtectionType
        metricsArtifactRef = event.metricsArtifactRef
        trainingLoss = event.trainingLoss
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
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        runtimeEngineJobId = event.runtimeEngineJobId
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        localUpdateArtifactRef = event.localUpdateArtifactRef
        encryptedUpdateArtifactRef = event.encryptedUpdateArtifactRef
        encryptedUpdateDigest = event.encryptedUpdateDigest
        modelUpdateArtifactRef = event.modelUpdateArtifactRef
        modelUpdateArtifactDigest = event.modelUpdateArtifactDigest
        updateProtectionType = event.updateProtectionType
        metricsArtifactRef = event.metricsArtifactRef
        trainingLoss = event.trainingLoss
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
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
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
        baseModelId = event.baseModelId
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
        baseModelId = event.baseModelId
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
        baseModelId = event.baseModelId
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
        baseModelId = event.baseModelId
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
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        runtimeEngineJobId = event.runtimeEngineJobId
        localModelId = event.localModelId
        updateArtifactId = event.updateArtifactId
        artifactRef = event.artifactRef
        artifactDigest = event.artifactDigest
        updateProtectionType = event.updateProtectionType
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
