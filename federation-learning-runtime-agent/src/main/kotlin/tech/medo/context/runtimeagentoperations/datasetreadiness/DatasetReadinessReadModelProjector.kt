package tech.medo.runtimeagentoperations.datasetreadiness

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidationFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetRejectedForTrainingEvent
import tech.medo.runtimeagentoperations.events.DatasetApprovedForTrainingEvent
import tech.medo.runtimeagentoperations.events.DatasetTrainingApprovalRevokedEvent



@Component
class DatasetReadinessReadModelProjector(private val repository: DatasetReadinessReadModelRepository) {
    @EventHandler
    fun on(
        event: DatasetDeclaredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.datasetUsage = event.datasetUsage
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessValidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.runtimeId = event.runtimeId
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.readable = event.readable
            entity.schemaReadable = event.schemaReadable
            entity.sampleBatchReadable = event.sampleBatchReadable
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessValidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.runtimeId = event.runtimeId
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessRevalidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.runtimeId = event.runtimeId
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.readable = event.readable
            entity.schemaReadable = event.schemaReadable
            entity.sampleBatchReadable = event.sampleBatchReadable
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessRevalidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.runtimeId = event.runtimeId
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: DatasetContractValidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.featureSchemaId = event.featureSchemaId
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.metadataReportId = event.metadataReportId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: DatasetContractValidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.featureSchemaId = event.featureSchemaId
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.metadataReportId = event.metadataReportId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: DatasetRejectedForTrainingEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: DatasetApprovedForTrainingEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: DatasetTrainingApprovalRevokedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
