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

        val entity = repository.findProjectionById(event.runtimeDatasetBindingId) ?: RuntimeDatasetMetadataCatalogReadModelProjection().apply {
                this.runtimeDatasetBindingId = event.runtimeDatasetBindingId
        }
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.metadataReportId = event.metadataReportId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeId = event.runtimeId
            entity.runtimeName = event.runtimeName
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetName = event.datasetName
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.missingValueRate = event.missingValueRate
            entity.duplicateRate = event.duplicateRate
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.classBalanceScore = event.classBalanceScore
            entity.profilingStatus = "MetadataReported"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: DatasetMetadataReprofiledEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeDatasetBindingId) ?: RuntimeDatasetMetadataCatalogReadModelProjection().apply {
                this.runtimeDatasetBindingId = event.runtimeDatasetBindingId
        }
            entity.runtimeDatasetBindingId = event.runtimeDatasetBindingId
            entity.metadataReportId = event.metadataReportId
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeId = event.runtimeId
            entity.runtimeName = event.runtimeName
            entity.featureSchemaId = event.featureSchemaId
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetName = event.datasetName
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.missingValueRate = event.missingValueRate
            entity.duplicateRate = event.duplicateRate
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.classBalanceScore = event.classBalanceScore
            entity.profilingStatus = "MetadataReported"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
