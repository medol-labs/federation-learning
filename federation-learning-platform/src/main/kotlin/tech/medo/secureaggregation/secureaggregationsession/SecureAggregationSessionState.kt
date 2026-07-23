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


@EventSourced(idType = UUID::class, tagKey = SecureAggregationSessionTags.SECURE_AGGREGATION_SESSION_ID)
class SecureAggregationSessionState @EntityCreator constructor() {

    var currentState: SecureAggregationSessionStateEnum? = null
    private var secureAggregationSessionId: UUID? = null
    private var trainingJobId: UUID? = null
    private var trainingRunConfigurationId: UUID? = null
    private var featureSchemaId: UUID? = null
    private var roundId: UUID? = null
    private var requiredParticipantCount: Int? = null
    private var acceptedRuntimeIds: List<UUID> = emptyList()
    private var selectedRuntimeIds: List<UUID> = emptyList()
    private var selectedParticipantCount: Int? = null
    private var encryptionScheme: String? = null
    private var publicKeyVersion: String? = null
    private var encryptedParameterScale: Int? = null
    private var submissionId: UUID? = null
    private var runtimeId: UUID? = null
    private var encryptedUpdateDigest: String? = null
    private var aggregatedModelVersionId: UUID? = null
    private var modelFormat: String? = null
    private var modelHash: String? = null
    private var failureReason: String? = null

    @EventSourcingHandler
    fun evolve(event: SecureAggregationSessionCreatedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.PLANNED
        secureAggregationSessionId = event.secureAggregationSessionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        requiredParticipantCount = event.requiredParticipantCount
        acceptedRuntimeIds = event.acceptedRuntimeIds
    }

    @EventSourcingHandler
    fun evolve(event: SecureAggregationParticipantsSelectedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.PARTICIPANTS_SELECTED
        secureAggregationSessionId = event.secureAggregationSessionId
        roundId = event.roundId
        acceptedRuntimeIds = event.acceptedRuntimeIds
        selectedRuntimeIds = event.selectedRuntimeIds
        selectedParticipantCount = event.selectedParticipantCount
    }

    @EventSourcingHandler
    fun evolve(event: HomomorphicEncryptionContextPreparedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.ENCRYPTION_CONTEXT_PREPARED
        secureAggregationSessionId = event.secureAggregationSessionId
        encryptionScheme = event.encryptionScheme
        publicKeyVersion = event.publicKeyVersion
        encryptedParameterScale = event.encryptedParameterScale
    }

    @EventSourcingHandler
    fun evolve(event: EncryptedModelUpdateReceivedEvent): SecureAggregationSessionState = apply {
        secureAggregationSessionId = event.secureAggregationSessionId
        submissionId = event.submissionId
        runtimeId = event.runtimeId
        encryptedUpdateDigest = event.encryptedUpdateDigest
    }

    @EventSourcingHandler
    fun evolve(event: SecureAggregationCompletedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.COMPLETED
        secureAggregationSessionId = event.secureAggregationSessionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        featureSchemaId = event.featureSchemaId
        roundId = event.roundId
        aggregatedModelVersionId = event.aggregatedModelVersionId
        modelFormat = event.modelFormat
        modelHash = event.modelHash
    }

    @EventSourcingHandler
    fun evolve(event: SecureAggregationFailedEvent): SecureAggregationSessionState = apply {
        currentState = SecureAggregationSessionStateEnum.FAILED
        secureAggregationSessionId = event.secureAggregationSessionId
        failureReason = event.failureReason
    }
}
