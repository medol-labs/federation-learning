package tech.medo.trainingorchestration.trainingjobdashboard

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingJobPausedEvent
import tech.medo.trainingorchestration.events.TrainingJobResumedEvent
import tech.medo.trainingorchestration.events.TrainingJobCanceledEvent
import tech.medo.trainingorchestration.events.TrainingJobCompletedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum


@Component
class TrainingJobDashboardReadModelProjector(private val repository: TrainingJobDashboardReadModelRepository) {
    @EventHandler
    fun on(event: FederationCreatedEvent) {
        // Skipped: FederationCreatedEvent does not provide enough key fields to locate TrainingJobDashboardReadModelProjection.
    }

    @EventHandler
    fun on(event: FeatureSchemaDefinedEvent) {
        // Skipped: FeatureSchemaDefinedEvent does not provide enough key fields to locate TrainingJobDashboardReadModelProjection.
    }

    @EventHandler
    fun on(
        event: TrainingJobCreatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingJobDashboardReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.federationId = event.federationId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.objective = event.objective
            entity.state = TrainingJobStateEnum.DRAFT
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingJobSubmittedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingJobDashboardReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.state = TrainingJobStateEnum.SUBMITTED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingJobPausedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingJobDashboardReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.state = TrainingJobStateEnum.PAUSED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingJobResumedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingJobDashboardReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.state = TrainingJobStateEnum.RUNNING
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingJobCanceledEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingJobDashboardReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.state = TrainingJobStateEnum.CANCELED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingJobCompletedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingJobDashboardReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.finalModelId = event.finalModelId
            entity.stopReason = event.stopReason
            entity.state = TrainingJobStateEnum.COMPLETED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRoundStartedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingJobDashboardReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.secureAggregationRequired = event.secureAggregationRequired
            entity.minimumNodesPerRound = event.minimumNodesPerRound
            entity.maxRounds = event.maxRounds
            entity.currentRoundNumber = event.roundNumber
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRoundCompletedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingJobId) ?: TrainingJobDashboardReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.maxRounds = event.maxRounds
            entity.globalAccuracy = event.globalAccuracy
            entity.currentRoundNumber = event.roundNumber
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
