package tech.medo.datasetgovernance.datasetcapability

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.DatasetDeclaredEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReprofiledEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent
import tech.medo.datasetgovernance.events.DatasetContractValidatedEvent
import tech.medo.datasetgovernance.events.DatasetContractValidationFailedEvent
import tech.medo.datasetgovernance.events.DatasetRejectedForTrainingEvent
import tech.medo.datasetgovernance.events.DatasetApprovedForTrainingEvent
import tech.medo.datasetgovernance.events.DatasetTrainingApprovalRevokedEvent


@Component
class DatasetCapabilityReadModelProjector(private val repository: DatasetCapabilityReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate DatasetCapabilityReadModelProjection.
    }

    @EventHandler
    fun on(event: FeatureSchemaDefinedEvent) {
        // Skipped: FeatureSchemaDefinedEvent does not provide enough key fields to locate DatasetCapabilityReadModelProjection.
    }

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
        event: DatasetMetadataReprofiledEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
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
        event: DatasetMetadataReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
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
