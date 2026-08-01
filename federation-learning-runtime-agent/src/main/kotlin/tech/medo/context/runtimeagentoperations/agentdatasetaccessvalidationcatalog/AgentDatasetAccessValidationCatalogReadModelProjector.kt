package tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidationFailedEvent



@Component
class AgentDatasetAccessValidationCatalogReadModelProjector(private val repository: AgentDatasetAccessValidationCatalogReadModelRepository) {
    @EventHandler
    fun on(event: DatasetDeclaredEvent) {
        // Skipped: DatasetDeclaredEvent does not provide enough key fields to locate AgentDatasetAccessValidationCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessValidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetAccessValidationId) ?: AgentDatasetAccessValidationCatalogReadModelProjection().apply {
                this.datasetAccessValidationId = event.datasetAccessValidationId
        }
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.featureSchemaId = event.featureSchemaId
            entity.runtimeId = event.runtimeId
            entity.readable = event.readable
            entity.schemaReadable = event.schemaReadable
            entity.sampleBatchReadable = event.sampleBatchReadable
            entity.validationStatus = "Checked"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessValidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetAccessValidationId) ?: AgentDatasetAccessValidationCatalogReadModelProjection().apply {
                this.datasetAccessValidationId = event.datasetAccessValidationId
        }
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.featureSchemaId = event.featureSchemaId
            entity.runtimeId = event.runtimeId
            entity.failureReason = event.failureReason
            entity.validationStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessRevalidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetAccessValidationId) ?: AgentDatasetAccessValidationCatalogReadModelProjection().apply {
                this.datasetAccessValidationId = event.datasetAccessValidationId
        }
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.featureSchemaId = event.featureSchemaId
            entity.runtimeId = event.runtimeId
            entity.readable = event.readable
            entity.schemaReadable = event.schemaReadable
            entity.sampleBatchReadable = event.sampleBatchReadable
            entity.validationStatus = "Checked"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessRevalidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetAccessValidationId) ?: AgentDatasetAccessValidationCatalogReadModelProjection().apply {
                this.datasetAccessValidationId = event.datasetAccessValidationId
        }
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.featureSchemaId = event.featureSchemaId
            entity.runtimeId = event.runtimeId
            entity.failureReason = event.failureReason
            entity.validationStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
