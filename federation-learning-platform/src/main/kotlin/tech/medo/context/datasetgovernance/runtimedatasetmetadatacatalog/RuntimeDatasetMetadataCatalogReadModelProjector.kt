package tech.medo.datasetgovernance.runtimedatasetmetadatacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReprofiledEvent



@Component
class RuntimeDatasetMetadataCatalogReadModelProjector(private val repository: RuntimeDatasetMetadataCatalogReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeDatasetMetadataCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: FeatureSchemaDefinedEvent) {
        // Skipped: FeatureSchemaDefinedEvent does not provide enough key fields to locate RuntimeDatasetMetadataCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: DatasetMetadataReportedEvent,
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
            entity.profilingStatus = "Reported"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: DatasetMetadataReprofiledEvent,
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
            entity.profilingStatus = "Reprofiled"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
