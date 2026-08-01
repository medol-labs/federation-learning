package tech.medo.trainingorchestration.trainingroundprogress

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartFailedEvent
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanDispatchedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionRejectedEvent
import tech.medo.trainingorchestration.events.SecureAggregationRequestedEvent
import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.trainingorchestration.events.GlobalModelEvaluationSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.events.TrainingRoundFailedEvent
import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


@Component
class TrainingRoundProgressReadModelProjector(private val repository: TrainingRoundProgressReadModelRepository) {
    @EventHandler
    fun on(event: FeatureSchemaDefinedEvent) {
        // Skipped: FeatureSchemaDefinedEvent does not provide enough key fields to locate TrainingRoundProgressReadModelProjection.
    }

    @EventHandler
    fun on(
        event: TrainingJobCreatedEvent,
        message: EventMessage
    ) {

        repository.findProjectionsByTrainingJobId(event.trainingJobId).forEach { entity ->
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.trainingJobObjective = event.objective
            ProjectionMetadata.assign(entity, message)
            repository.save(entity)
        }
    }

    @EventHandler
    fun on(
        event: TrainingRoundParticipantsSelectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.selectedOrganizationIds = event.selectedOrganizationIds
            entity.selectedParticipants = event.selectedParticipants
            entity.selectedOrganizationCount = event.selectedOrganizationCount
            entity.selectedRuntimeCount = event.selectedRuntimeCount
            entity.minimumNodesPerRound = event.minimumNodesPerRound
            entity.state = TrainingRoundStateEnum.PARTICIPANTS_SELECTED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRoundStartedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.selectedOrganizationIds = event.selectedOrganizationIds
            entity.selectedParticipants = event.selectedParticipants
            entity.selectedOrganizationCount = event.selectedOrganizationCount
            entity.selectedRuntimeCount = event.selectedRuntimeCount
            entity.minimumNodesPerRound = event.minimumNodesPerRound
            entity.state = TrainingRoundStateEnum.RUNNING
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRoundStartFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.selectedOrganizationIds = event.selectedOrganizationIds
            entity.selectedParticipants = event.selectedParticipants
            entity.selectedOrganizationCount = event.selectedOrganizationCount
            entity.selectedRuntimeCount = event.selectedRuntimeCount
            entity.minimumNodesPerRound = event.minimumNodesPerRound
            entity.failureReason = event.failureReason
            entity.state = TrainingRoundStateEnum.FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantExecutionPlanGeneratedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.baseModelVersionId = event.baseModelVersionId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ParticipantExecutionPlanDispatchedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.baseModelVersionId = event.baseModelVersionId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelUpdateSubmissionReceivedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelUpdateSubmissionAcceptedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelUpdateSubmissionRejectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: SecureAggregationRequestedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.acceptedModelUpdateCount = event.acceptedModelUpdateCount
            entity.minimumNodesPerRound = event.minimumNodesPerRound
            entity.state = TrainingRoundStateEnum.AGGREGATING
            entity.secureAggregationStatus = "Aggregating"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: GlobalModelUpdatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.aggregatedModelVersionId = event.aggregatedModelVersionId
            entity.state = TrainingRoundStateEnum.EVALUATING_GLOBAL_MODEL
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: GlobalModelEvaluationSubmittedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.aggregatedModelVersionId = event.aggregatedModelVersionId
            entity.globalAccuracy = event.globalAccuracy
            entity.globalFairnessScore = event.globalFairnessScore
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRoundCompletedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.aggregatedModelVersionId = event.aggregatedModelVersionId
            entity.globalAccuracy = event.globalAccuracy
            entity.state = TrainingRoundStateEnum.COMPLETED
            entity.completedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRoundFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(TrainingRoundProgressReadModelKey(trainingJobId = event.trainingJobId, roundId = event.roundId)) ?: TrainingRoundProgressReadModelProjection().apply {
                this.trainingJobId = event.trainingJobId
                this.roundId = event.roundId
        }
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.featureSchemaId = event.featureSchemaId
            entity.roundId = event.roundId
            entity.selectedRuntimeCount = event.selectedRuntimeCount
            entity.minimumNodesPerRound = event.minimumNodesPerRound
            entity.failureReason = event.failureReason
            entity.state = TrainingRoundStateEnum.FAILED
            entity.failedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}
