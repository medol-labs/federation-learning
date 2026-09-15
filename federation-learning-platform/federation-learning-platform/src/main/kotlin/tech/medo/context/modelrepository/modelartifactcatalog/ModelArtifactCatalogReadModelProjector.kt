package tech.medo.modelrepository.modelartifactcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import tech.medo.modelrepository.events.FederatedModelArtifactRegisteredEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


@Namespace("readmodel-model-artifact-catalog")
@Component
class ModelArtifactCatalogReadModelProjector(private val repository: ModelArtifactCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: ModelArtifactRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelArtifactCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.modelName = event.modelName
            entity.modelVersion = event.modelVersion
            entity.modelDescription = event.modelDescription
            entity.sourceType = event.sourceType
            entity.modelArtifactUri = event.modelArtifactUri
            entity.modelRegistryRef = event.modelRegistryRef
            entity.modelFormat = event.modelFormat
            entity.modelArtifactDigest = event.modelArtifactDigest
            entity.modelSignatureUri = event.modelSignatureUri
            entity.modelSizeBytes = event.modelSizeBytes
            entity.state = ModelArtifactStateEnum.REGISTERED
            entity.registeredAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FederatedModelArtifactRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelArtifactCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.modelName = event.modelName
            entity.modelVersion = event.modelVersion
            entity.modelDescription = event.modelDescription
            entity.sourceType = event.sourceType
            entity.modelArtifactUri = event.modelArtifactUri
            entity.modelRegistryRef = event.modelRegistryRef
            entity.modelFormat = event.modelFormat
            entity.modelArtifactDigest = event.modelArtifactDigest
            entity.modelSignatureUri = event.modelSignatureUri
            entity.modelSizeBytes = event.modelSizeBytes
            entity.trainingJobId = event.trainingJobId
            entity.roundId = event.roundId
            entity.trainingJobObjective = event.trainingJobObjective
            entity.state = ModelArtifactStateEnum.REGISTERED
            entity.registeredAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(event: TrainingJobCreatedEvent) {
        // Skipped: TrainingJobCreatedEvent does not provide enough key fields to locate ModelArtifactCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: GlobalModelUpdatedEvent) {
        // Skipped: GlobalModelUpdatedEvent does not provide enough key fields to locate ModelArtifactCatalogReadModelProjection.
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}
