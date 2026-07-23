package tech.medo.modellifecycle.modelversioncatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.modellifecycle.events.ModelCandidateRegisteredEvent
import tech.medo.modellifecycle.events.ModelEvaluationPackageRecordedEvent
import tech.medo.modellifecycle.events.ModelApprovedEvent
import tech.medo.modellifecycle.events.ModelPromotedToProductionEvent
import tech.medo.modellifecycle.events.ModelVersionRolledBackEvent
import tech.medo.modellifecycle.events.ModelVersionRetiredEvent
import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum

@Component
class ModelVersionCatalogReadModelProjector(private val repository: ModelVersionCatalogReadModelRepository) {
    @EventHandler
    fun on(event: TrainingJobCreatedEvent) {
        // Skipped: TrainingJobCreatedEvent does not provide enough key fields to locate ModelVersionCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: ModelCandidateRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelVersionId) ?: ModelVersionCatalogReadModelProjection().apply {
                this.modelVersionId = event.modelVersionId
        }
            entity.modelVersionId = event.modelVersionId
            entity.trainingJobId = event.trainingJobId
            entity.finalRoundId = event.finalRoundId
            entity.modelArtifactId = event.modelArtifactId
            entity.modelHash = event.modelHash
            entity.evaluationReportId = event.evaluationReportId
            entity.finalGlobalAccuracy = event.finalGlobalAccuracy
            entity.state = ModelVersionStateEnum.CANDIDATE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelEvaluationPackageRecordedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelVersionId) ?: ModelVersionCatalogReadModelProjection().apply {
                this.modelVersionId = event.modelVersionId
        }
            entity.modelVersionId = event.modelVersionId
            entity.trainingJobId = event.trainingJobId
            entity.evaluationReportId = event.evaluationReportId
            entity.experimentId = event.experimentId
            entity.hyperparameterSnapshotId = event.hyperparameterSnapshotId
            entity.reproducibilityManifestId = event.reproducibilityManifestId
            entity.modelCardId = event.modelCardId
            entity.baselineModelVersionId = event.baselineModelVersionId
            entity.state = ModelVersionStateEnum.EVALUATION_PACKAGED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelApprovedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelVersionId) ?: ModelVersionCatalogReadModelProjection().apply {
                this.modelVersionId = event.modelVersionId
        }
            entity.modelVersionId = event.modelVersionId
            entity.state = ModelVersionStateEnum.APPROVED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelPromotedToProductionEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelVersionId) ?: ModelVersionCatalogReadModelProjection().apply {
                this.modelVersionId = event.modelVersionId
        }
            entity.modelVersionId = event.modelVersionId
            entity.releaseChannel = event.releaseChannel
            entity.productionStage = event.productionStage
            entity.state = ModelVersionStateEnum.PRODUCTION
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelVersionRolledBackEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelVersionId) ?: ModelVersionCatalogReadModelProjection().apply {
                this.modelVersionId = event.modelVersionId
        }
            entity.modelVersionId = event.modelVersionId
            entity.previousModelVersionId = event.previousModelVersionId
            entity.state = ModelVersionStateEnum.ROLLED_BACK
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: ModelVersionRetiredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelVersionId) ?: ModelVersionCatalogReadModelProjection().apply {
                this.modelVersionId = event.modelVersionId
        }
            entity.modelVersionId = event.modelVersionId
            entity.state = ModelVersionStateEnum.RETIRED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }
}
