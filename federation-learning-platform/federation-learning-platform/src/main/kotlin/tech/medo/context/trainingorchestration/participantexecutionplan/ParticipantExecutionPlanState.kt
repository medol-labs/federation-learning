package tech.medo.trainingorchestration.participantexecutionplan

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanDispatchedEvent
import tech.medo.trainingorchestration.domain.states.ParticipantExecutionPlanStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = ParticipantExecutionPlanTags.EXECUTION_PLAN_ID)
class ParticipantExecutionPlanState @EntityCreator constructor() {

    var currentState: ParticipantExecutionPlanStateEnum? = null
    var executionPlanId: UUID? = null
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

    @EventSourcingHandler
    fun evolve(event: ParticipantExecutionPlanGeneratedEvent): ParticipantExecutionPlanState = apply {
        currentState = ParticipantExecutionPlanStateEnum.PLAN_GENERATED
        executionPlanId = event.executionPlanId
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
    fun evolve(event: ParticipantExecutionPlanDispatchedEvent): ParticipantExecutionPlanState = apply {
        currentState = ParticipantExecutionPlanStateEnum.PLAN_DISPATCHED
        executionPlanId = event.executionPlanId
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
}
