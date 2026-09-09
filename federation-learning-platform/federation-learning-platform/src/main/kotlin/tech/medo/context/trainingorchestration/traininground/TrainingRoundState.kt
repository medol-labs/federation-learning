package tech.medo.trainingorchestration.traininground

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionFailedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionRetryRequestedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartFailedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionRejectedEvent
import tech.medo.trainingorchestration.events.PlainModelAggregationCompletedEvent
import tech.medo.trainingorchestration.events.SecureAggregationRequestedEvent
import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.trainingorchestration.events.GlobalModelEvaluationSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.events.TrainingRoundFailedEvent
import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum

import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;


@EventSourced(idType = UUID::class, tagKey = TrainingRoundTags.TRAINING_JOB_ID)
class TrainingRoundState @EntityCreator constructor() {

    var currentState: TrainingRoundStateEnum? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    var roundId: UUID? = null
    var roundNumber: Int? = null
    var maxRounds: Int? = null
    var minimumAccuracy: BigDecimal? = null
    var aggregationAlgorithm: String? = null
    var minimumNodesPerRound: Int? = null
    var secureAggregationRequired: Boolean? = null
    var selectedOrganizationIds: List<UUID> = emptyList()
    var selectedRuntimeIds: List<UUID> = emptyList()
    var selectedParticipants: List<TrainingRoundParticipant> = emptyList()
    var selectedOrganizationCount: Int? = null
    var selectedRuntimeCount: Int? = null
    var failureReason: String? = null
    var secureAggregationSessionId: UUID? = null
    var encryptionScheme: String? = null
    var publicKeyVersion: String? = null
    var publicKeyRef: String? = null
    var encryptedParameterScale: Int? = null
    var modelUpdateSubmissionId: UUID? = null
    var executionSessionId: UUID? = null
    var executionPlanId: UUID? = null
    var roundExecutionId: UUID? = null
    var runtimeId: UUID? = null
    var localModelId: UUID? = null
    var updateArtifactId: UUID? = null
    var artifactRef: String? = null
    var artifactDigest: String? = null
    var updateProtectionType: String? = null
    var trainingLoss: BigDecimal? = null
    var anomalyScore: BigDecimal? = null
    var acceptedModelUpdateCount: Int? = null
    var acceptedRuntimeIds: List<UUID> = emptyList()
    var acceptedModelUpdateArtifactRefs: List<String> = emptyList()
    var requiredModelUpdateCount: Int? = null
    var plainAggregationReady: Boolean? = null
    var rejectionReason: String? = null
    var aggregatedModelId: UUID? = null
    var aggregatedModelName: String? = null
    var aggregatedModelVersion: String? = null
    var aggregatedModelDescription: String? = null
    var modelSourceType: String? = null
    var aggregatedModelArtifactUri: String? = null
    var aggregatedModelRegistryRef: String? = null
    var modelFormat: String? = null
    var modelArtifactDigest: String? = null
    var aggregatedModelSignatureUri: String? = null
    var aggregatedModelSizeBytes: Int? = null
    var requiredParticipantCount: Int? = null
    var globalAccuracy: BigDecimal? = null
    var globalFairnessScore: BigDecimal? = null

    @EventSourcingHandler
    fun evolve(event: TrainingRoundParticipantsSelectedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.PARTICIPANTS_SELECTED
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        aggregationAlgorithm = event.aggregationAlgorithm
        minimumNodesPerRound = event.minimumNodesPerRound
        secureAggregationRequired = event.secureAggregationRequired
        selectedOrganizationIds = event.selectedOrganizationIds
        selectedRuntimeIds = event.selectedRuntimeIds
        selectedParticipants = event.selectedParticipants
        selectedOrganizationCount = event.selectedOrganizationCount
        selectedRuntimeCount = event.selectedRuntimeCount
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRoundParticipantSelectionFailedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.FAILED
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        aggregationAlgorithm = event.aggregationAlgorithm
        minimumNodesPerRound = event.minimumNodesPerRound
        secureAggregationRequired = event.secureAggregationRequired
        selectedOrganizationIds = event.selectedOrganizationIds
        selectedRuntimeIds = event.selectedRuntimeIds
        selectedParticipants = event.selectedParticipants
        selectedOrganizationCount = event.selectedOrganizationCount
        selectedRuntimeCount = event.selectedRuntimeCount
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRoundParticipantSelectionRetryRequestedEvent): TrainingRoundState = apply {
        trainingJobId = event.trainingJobId
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRoundStartedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.RUNNING
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        selectedOrganizationIds = event.selectedOrganizationIds
        selectedRuntimeIds = event.selectedRuntimeIds
        selectedParticipants = event.selectedParticipants
        selectedOrganizationCount = event.selectedOrganizationCount
        selectedRuntimeCount = event.selectedRuntimeCount
        minimumNodesPerRound = event.minimumNodesPerRound
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        aggregationAlgorithm = event.aggregationAlgorithm
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        publicKeyRef = event.publicKeyRef
        encryptedParameterScale = event.encryptedParameterScale
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRoundStartFailedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.FAILED
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        selectedOrganizationIds = event.selectedOrganizationIds
        selectedRuntimeIds = event.selectedRuntimeIds
        selectedParticipants = event.selectedParticipants
        selectedOrganizationCount = event.selectedOrganizationCount
        selectedRuntimeCount = event.selectedRuntimeCount
        minimumNodesPerRound = event.minimumNodesPerRound
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        publicKeyRef = event.publicKeyRef
        encryptedParameterScale = event.encryptedParameterScale
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: ModelUpdateSubmissionReceivedEvent): TrainingRoundState = apply {
        modelUpdateSubmissionId = event.modelUpdateSubmissionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundExecutionId = event.roundExecutionId
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        localModelId = event.localModelId
        updateArtifactId = event.updateArtifactId
        artifactRef = event.artifactRef
        artifactDigest = event.artifactDigest
        updateProtectionType = event.updateProtectionType
        trainingLoss = event.trainingLoss
    }

    @EventSourcingHandler
    fun evolve(event: ModelUpdateSubmissionAcceptedEvent): TrainingRoundState = apply {
        modelUpdateSubmissionId = event.modelUpdateSubmissionId
        executionSessionId = event.executionSessionId
        executionPlanId = event.executionPlanId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundNumber = event.roundNumber
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        aggregationAlgorithm = event.aggregationAlgorithm
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
        secureAggregationRequired = event.secureAggregationRequired
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        updateArtifactId = event.updateArtifactId
        artifactRef = event.artifactRef
        artifactDigest = event.artifactDigest
        updateProtectionType = event.updateProtectionType
        anomalyScore = event.anomalyScore
        acceptedModelUpdateCount = event.acceptedModelUpdateCount
        acceptedRuntimeIds = event.acceptedRuntimeIds
        acceptedModelUpdateArtifactRefs = event.acceptedModelUpdateArtifactRefs
        minimumNodesPerRound = event.minimumNodesPerRound
        requiredModelUpdateCount = event.requiredModelUpdateCount
        plainAggregationReady = event.plainAggregationReady
    }

    @EventSourcingHandler
    fun evolve(event: ModelUpdateSubmissionRejectedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.EVALUATING_GLOBAL_MODEL
        modelUpdateSubmissionId = event.modelUpdateSubmissionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        runtimeId = event.runtimeId
        updateArtifactId = event.updateArtifactId
        anomalyScore = event.anomalyScore
        rejectionReason = event.rejectionReason
    }

    @EventSourcingHandler
    fun evolve(event: PlainModelAggregationCompletedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.COMPLETED
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        aggregatedModelId = event.aggregatedModelId
        aggregatedModelName = event.aggregatedModelName
        aggregatedModelVersion = event.aggregatedModelVersion
        aggregatedModelDescription = event.aggregatedModelDescription
        modelSourceType = event.modelSourceType
        aggregatedModelArtifactUri = event.aggregatedModelArtifactUri
        aggregatedModelRegistryRef = event.aggregatedModelRegistryRef
        modelFormat = event.modelFormat
        modelArtifactDigest = event.modelArtifactDigest
        aggregatedModelSignatureUri = event.aggregatedModelSignatureUri
        aggregatedModelSizeBytes = event.aggregatedModelSizeBytes
    }

    @EventSourcingHandler
    fun evolve(event: SecureAggregationRequestedEvent): TrainingRoundState = apply {
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        requiredParticipantCount = event.requiredParticipantCount
        selectedOrganizationIds = event.selectedOrganizationIds
        selectedRuntimeIds = event.selectedRuntimeIds
        selectedOrganizationCount = event.selectedOrganizationCount
        selectedRuntimeCount = event.selectedRuntimeCount
        minimumNodesPerRound = event.minimumNodesPerRound
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        secureAggregationRequired = event.secureAggregationRequired
    }

    @EventSourcingHandler
    fun evolve(event: GlobalModelUpdatedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.EVALUATING_GLOBAL_MODEL
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        secureAggregationSessionId = event.secureAggregationSessionId
        aggregatedModelId = event.aggregatedModelId
        aggregatedModelName = event.aggregatedModelName
        aggregatedModelVersion = event.aggregatedModelVersion
        aggregatedModelDescription = event.aggregatedModelDescription
        modelSourceType = event.modelSourceType
        aggregatedModelArtifactUri = event.aggregatedModelArtifactUri
        aggregatedModelRegistryRef = event.aggregatedModelRegistryRef
        modelFormat = event.modelFormat
        modelArtifactDigest = event.modelArtifactDigest
        aggregatedModelSignatureUri = event.aggregatedModelSignatureUri
        aggregatedModelSizeBytes = event.aggregatedModelSizeBytes
    }

    @EventSourcingHandler
    fun evolve(event: GlobalModelEvaluationSubmittedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.EVALUATING_GLOBAL_MODEL
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        aggregatedModelId = event.aggregatedModelId
        aggregatedModelArtifactUri = event.aggregatedModelArtifactUri
        aggregatedModelRegistryRef = event.aggregatedModelRegistryRef
        modelFormat = event.modelFormat
        modelArtifactDigest = event.modelArtifactDigest
        aggregatedModelSignatureUri = event.aggregatedModelSignatureUri
        globalAccuracy = event.globalAccuracy
        globalFairnessScore = event.globalFairnessScore
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRoundCompletedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.COMPLETED
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        aggregatedModelId = event.aggregatedModelId
        aggregatedModelArtifactUri = event.aggregatedModelArtifactUri
        aggregatedModelRegistryRef = event.aggregatedModelRegistryRef
        modelFormat = event.modelFormat
        modelArtifactDigest = event.modelArtifactDigest
        aggregatedModelSignatureUri = event.aggregatedModelSignatureUri
        globalAccuracy = event.globalAccuracy
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRoundFailedEvent): TrainingRoundState = apply {
        currentState = TrainingRoundStateEnum.FAILED
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        selectedRuntimeCount = event.selectedRuntimeCount
        minimumNodesPerRound = event.minimumNodesPerRound
        failureReason = event.failureReason
    }
}
