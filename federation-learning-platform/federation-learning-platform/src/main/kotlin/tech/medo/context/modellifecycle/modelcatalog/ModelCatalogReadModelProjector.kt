package tech.medo.modellifecycle.modelcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.modellifecycle.events.ModelCandidateRegisteredEvent
import tech.medo.modellifecycle.events.ModelEvaluationPackageRecordedEvent
import tech.medo.modellifecycle.events.ModelApprovedEvent
import tech.medo.modellifecycle.events.ModelPromotedToProductionEvent
import tech.medo.modellifecycle.events.ModelRolledBackEvent
import tech.medo.modellifecycle.events.ModelRetiredEvent
import tech.medo.modellifecycle.domain.states.ModelStateEnum


@Component
class ModelCatalogReadModelProjector(private val repository: ModelCatalogReadModelRepository) {
    @EventHandler
    fun on(event: TrainingJobCreatedEvent) {
        // Skipped: TrainingJobCreatedEvent does not provide enough key fields to locate ModelCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: ModelCandidateRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.trainingJobId = event.trainingJobId
            entity.finalRoundId = event.finalRoundId
            entity.modelArtifactId = event.modelArtifactId
            entity.modelArtifactDigest = event.modelArtifactDigest
            entity.evaluationReportId = event.evaluationReportId
            entity.finalGlobalAccuracy = event.finalGlobalAccuracy
            entity.state = ModelStateEnum.CANDIDATE
            entity.previousModelId = event.modelId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelEvaluationPackageRecordedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.trainingJobId = event.trainingJobId
            entity.evaluationReportId = event.evaluationReportId
            entity.experimentId = event.experimentId
            entity.hyperparameterSnapshotId = event.hyperparameterSnapshotId
            entity.reproducibilityManifestId = event.reproducibilityManifestId
            entity.modelCardId = event.modelCardId
            entity.baselineModelId = event.baselineModelId
            entity.state = ModelStateEnum.EVALUATION_PACKAGED
            entity.previousModelId = event.modelId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelApprovedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.state = ModelStateEnum.APPROVED
            entity.previousModelId = event.modelId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelPromotedToProductionEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.releaseChannel = event.releaseChannel
            entity.productionStage = event.productionStage
            entity.state = ModelStateEnum.PRODUCTION
            entity.previousModelId = event.modelId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelRolledBackEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.previousModelId = event.previousModelId
            entity.state = ModelStateEnum.ROLLED_BACK
            entity.baselineModelId = event.modelId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelRetiredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.state = ModelStateEnum.RETIRED
            entity.previousModelId = event.modelId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
