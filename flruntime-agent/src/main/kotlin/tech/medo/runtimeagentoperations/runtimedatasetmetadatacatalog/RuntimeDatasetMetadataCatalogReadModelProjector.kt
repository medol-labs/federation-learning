package tech.medo.runtimeagentoperations.runtimedatasetmetadatacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetProfilingFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofiledEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofilingFailedEvent


@Component
class RuntimeDatasetMetadataCatalogReadModelProjector(private val repository: RuntimeDatasetMetadataCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: AgentDatasetMetadataReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.metadataReportId) ?: RuntimeDatasetMetadataCatalogReadModelProjection().apply {
                this.metadataReportId = event.metadataReportId
        }
            entity.metadataReportId = event.metadataReportId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.missingValueRate = event.missingValueRate
            entity.duplicateRate = event.duplicateRate
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.classBalanceScore = event.classBalanceScore
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetProfilingFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.metadataReportId) ?: RuntimeDatasetMetadataCatalogReadModelProjection().apply {
                this.metadataReportId = event.metadataReportId
        }
            entity.metadataReportId = event.metadataReportId
            entity.datasetId = event.datasetId
            entity.runtimeId = event.runtimeId
            entity.failureReason = event.failureReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetReprofiledEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.metadataReportId) ?: RuntimeDatasetMetadataCatalogReadModelProjection().apply {
                this.metadataReportId = event.metadataReportId
        }
            entity.metadataReportId = event.metadataReportId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.missingValueRate = event.missingValueRate
            entity.duplicateRate = event.duplicateRate
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.classBalanceScore = event.classBalanceScore
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: AgentDatasetReprofilingFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.metadataReportId) ?: RuntimeDatasetMetadataCatalogReadModelProjection().apply {
                this.metadataReportId = event.metadataReportId
        }
            entity.metadataReportId = event.metadataReportId
            entity.datasetId = event.datasetId
            entity.runtimeId = event.runtimeId
            entity.failureReason = event.failureReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }
}
