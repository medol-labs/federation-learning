package tech.medo.trainingorchestration.trainingjob

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingJobPausedEvent
import tech.medo.trainingorchestration.events.TrainingJobResumedEvent
import tech.medo.trainingorchestration.events.TrainingJobCanceledEvent
import tech.medo.trainingorchestration.events.TrainingJobCompletedEvent
import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = TrainingJobTags.TRAINING_JOB_ID)
class TrainingJobState @EntityCreator constructor() {

    var currentState: TrainingJobStateEnum? = null
    var trainingJobId: UUID? = null
    var federationId: UUID? = null
    var federationName: String? = null
    var initialModelId: UUID? = null
    var featureSchemaId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var configurationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var trainingJobObjective: String? = null
    var objective: String? = null
    var pauseReason: String? = null
    var resumeReason: String? = null
    var cancelReason: String? = null
    var finalRoundId: UUID? = null
    var finalModelId: UUID? = null
    var stopReason: String? = null

    @EventSourcingHandler
    fun evolve(event: TrainingJobCreatedEvent): TrainingJobState = apply {
        currentState = TrainingJobStateEnum.DRAFT
        trainingJobId = event.trainingJobId
        federationId = event.federationId
        federationName = event.federationName
        initialModelId = event.initialModelId
        featureSchemaId = event.featureSchemaId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        configurationName = event.configurationName
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        trainingJobObjective = event.trainingJobObjective
        objective = event.objective
    }

    @EventSourcingHandler
    fun evolve(event: TrainingJobSubmittedEvent): TrainingJobState = apply {
        currentState = TrainingJobStateEnum.SUBMITTED
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        federationId = event.federationId
        federationName = event.federationName
        configurationName = event.configurationName
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        trainingJobObjective = event.trainingJobObjective
        objective = event.objective
    }

    @EventSourcingHandler
    fun evolve(event: TrainingJobPausedEvent): TrainingJobState = apply {
        currentState = TrainingJobStateEnum.PAUSED
        trainingJobId = event.trainingJobId
        pauseReason = event.pauseReason
    }

    @EventSourcingHandler
    fun evolve(event: TrainingJobResumedEvent): TrainingJobState = apply {
        currentState = TrainingJobStateEnum.RUNNING
        trainingJobId = event.trainingJobId
        resumeReason = event.resumeReason
    }

    @EventSourcingHandler
    fun evolve(event: TrainingJobCanceledEvent): TrainingJobState = apply {
        currentState = TrainingJobStateEnum.CANCELED
        trainingJobId = event.trainingJobId
        cancelReason = event.cancelReason
    }

    @EventSourcingHandler
    fun evolve(event: TrainingJobCompletedEvent): TrainingJobState = apply {
        currentState = TrainingJobStateEnum.COMPLETED
        trainingJobId = event.trainingJobId
        finalRoundId = event.finalRoundId
        finalModelId = event.finalModelId
        trainingJobObjective = event.trainingJobObjective
        stopReason = event.stopReason
    }
}
