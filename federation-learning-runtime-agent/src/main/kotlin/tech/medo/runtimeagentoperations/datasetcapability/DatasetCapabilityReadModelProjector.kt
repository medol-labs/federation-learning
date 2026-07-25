package tech.medo.runtimeagentoperations.datasetcapability

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetRejectedForTrainingEvent
import tech.medo.runtimeagentoperations.events.DatasetApprovedForTrainingEvent
import tech.medo.runtimeagentoperations.events.DatasetTrainingApprovalRevokedEvent



@Component
class DatasetCapabilityReadModelProjector(private val repository: DatasetCapabilityReadModelRepository) {
    @EventHandler
    fun on(
        event: DatasetDeclaredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
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
        event: DatasetContractValidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
