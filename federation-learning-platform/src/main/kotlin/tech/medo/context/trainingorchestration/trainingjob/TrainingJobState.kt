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
    private var trainingJobId: UUID? = null
    private var federationId: UUID? = null
    private var featureSchemaId: UUID? = null
    private var trainingRunConfigurationId: UUID? = null
    private var objective: String? = null
    private var pauseReason: String? = null
    private var resumeReason: String? = null
    private var cancelReason: String? = null
    private var finalRoundId: UUID? = null
    private var finalModelVersionId: UUID? = null
    private var stopReason: String? = null

    @EventSourcingHandler
    fun evolve(event: TrainingJobCreatedEvent): TrainingJobState = apply {
        currentState = TrainingJobStateEnum.DRAFT
        trainingJobId = event.trainingJobId
        federationId = event.federationId
        featureSchemaId = event.featureSchemaId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        objective = event.objective
    }

    @EventSourcingHandler
    fun evolve(event: TrainingJobSubmittedEvent): TrainingJobState = apply {
        currentState = TrainingJobStateEnum.SUBMITTED
        trainingJobId = event.trainingJobId
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
        finalModelVersionId = event.finalModelVersionId
        stopReason = event.stopReason
    }
}
