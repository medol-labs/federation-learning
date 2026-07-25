package tech.medo.modelrepository.modelartifactcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


@Component
class ModelArtifactCatalogReadModelProjector(private val repository: ModelArtifactCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: ModelArtifactRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelVersionId) ?: ModelArtifactCatalogReadModelProjection().apply {
                this.modelVersionId = event.modelVersionId
        }
            entity.modelVersionId = event.modelVersionId
            entity.modelArtifactRef = event.modelArtifactRef
            entity.modelRepositoryRef = event.modelRepositoryRef
            entity.modelFormat = event.modelFormat
            entity.modelHash = event.modelHash
            entity.modelSignatureRef = event.modelSignatureRef
            entity.modelSizeBytes = event.modelSizeBytes
            entity.sourceType = event.sourceType
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
