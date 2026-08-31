package tech.medo.secureaggregation.secureaggregationsession

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.secureaggregation.events.SecureAggregationSessionCreatedEvent
import tech.medo.secureaggregation.events.SecureAggregationParticipantsSelectedEvent
import tech.medo.secureaggregation.events.HomomorphicEncryptionContextPreparedEvent
import tech.medo.secureaggregation.events.EncryptedModelUpdateReceivedEvent
import tech.medo.secureaggregation.events.SecureAggregationCompletedEvent
import tech.medo.secureaggregation.events.SecureAggregationFailedEvent
import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = SecureAggregationSessionTags.SECURE_AGGREGATION_SESSION_ID)
class SecureAggregationSessionState @EntityCreator constructor() {

    var currentState: SecureAggregationSessionStateEnum? = null
    var secureAggregationSessionId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    var roundId: UUID? = null
    var roundNumber: Int? = null
    var requiredParticipantCount: Int? = null
    var selectedOrganizationIds: List<UUID> = emptyList()
    var selectedRuntimeIds: List<UUID> = emptyList()
    var selectedOrganizationCount: Int? = null
    var selectedRuntimeCount: Int? = null
    var minimumNodesPerRound: Int? = null
    var maxRounds: Int? = null
    var minimumAccuracy: BigDecimal? = null
    var secureAggregationRequired: Boolean? = null
    var selectedParticipantCount: Int? = null
    var encryptionScheme: String? = null
    var publicKeyVersion: String? = null
    var publicKeyRef: String? = null
    var encryptedParameterScale: Int? = null
    var submissionId: UUID? = null
    var runtimeId: UUID? = null
    var updateArtifactId: UUID? = null
    var encryptedUpdateArtifactRef: String? = null
    var encryptedUpdateDigest: String? = null
    var aggregatedModelId: UUID? = null
    var aggregatedModelArtifactUri: String? = null
    var aggregatedModelRegistryRef: String? = null
    var modelFormat: String? = null
    var modelArtifactDigest: String? = null
    var aggregatedModelSignatureUri: String? = null
    var failureReason: String? = null

    @EventSourcingHandler
    fun evolve(event: SecureAggregationSessionCreatedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.PLANNED
        secureAggregationSessionId = event.secureAggregationSessionId
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
    fun evolve(event: SecureAggregationParticipantsSelectedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.PARTICIPANTS_SELECTED
        secureAggregationSessionId = event.secureAggregationSessionId
        roundId = event.roundId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundNumber = event.roundNumber
        selectedOrganizationIds = event.selectedOrganizationIds
        selectedRuntimeIds = event.selectedRuntimeIds
        selectedOrganizationCount = event.selectedOrganizationCount
        selectedParticipantCount = event.selectedParticipantCount
        minimumNodesPerRound = event.minimumNodesPerRound
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        secureAggregationRequired = event.secureAggregationRequired
    }

    @EventSourcingHandler
    fun evolve(event: HomomorphicEncryptionContextPreparedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.ENCRYPTION_CONTEXT_PREPARED
        secureAggregationSessionId = event.secureAggregationSessionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        selectedOrganizationIds = event.selectedOrganizationIds
        selectedRuntimeIds = event.selectedRuntimeIds
        selectedOrganizationCount = event.selectedOrganizationCount
        selectedRuntimeCount = event.selectedRuntimeCount
        minimumNodesPerRound = event.minimumNodesPerRound
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        secureAggregationRequired = event.secureAggregationRequired
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        publicKeyRef = event.publicKeyRef
        encryptedParameterScale = event.encryptedParameterScale
    }

    @EventSourcingHandler
    fun evolve(event: EncryptedModelUpdateReceivedEvent): SecureAggregationSessionState = apply {
        secureAggregationSessionId = event.secureAggregationSessionId
        submissionId = event.submissionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        roundNumber = event.roundNumber
        maxRounds = event.maxRounds
        minimumAccuracy = event.minimumAccuracy
        runtimeId = event.runtimeId
        updateArtifactId = event.updateArtifactId
        encryptedUpdateArtifactRef = event.encryptedUpdateArtifactRef
        encryptedUpdateDigest = event.encryptedUpdateDigest
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
    }

    @EventSourcingHandler
    fun evolve(event: SecureAggregationCompletedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.COMPLETED
        secureAggregationSessionId = event.secureAggregationSessionId
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
    }

    @EventSourcingHandler
    fun evolve(event: SecureAggregationFailedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.FAILED
        secureAggregationSessionId = event.secureAggregationSessionId
        failureReason = event.failureReason
    }
}
