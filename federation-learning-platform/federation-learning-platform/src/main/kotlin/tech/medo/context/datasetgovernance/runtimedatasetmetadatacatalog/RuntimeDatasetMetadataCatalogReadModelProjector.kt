package tech.medo.datasetgovernance.runtimedatasetmetadatacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReprofiledEvent



interface RuntimeDatasetMetadataCatalogReadModelProjectionUpdater {
    fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetMetadataReportedEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetMetadataReprofiledEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(RuntimeDatasetMetadataCatalogReadModelProjectionUpdater::class)
class DefaultRuntimeDatasetMetadataCatalogReadModelProjectionUpdater(
    private val repository: RuntimeDatasetMetadataCatalogReadModelRepository
) : RuntimeDatasetMetadataCatalogReadModelProjectionUpdater {
    override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeDatasetMetadataCatalogReadModelProjection.
    }

    override fun update(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    ) {
        // Skipped: FeatureSchemaDefinedEvent does not provide enough key fields to locate RuntimeDatasetMetadataCatalogReadModelProjection.
    }

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

@Namespace("readmodel-runtime-dataset-metadata-catalog")
@Component
class RuntimeDatasetMetadataCatalogReadModelProjector(
    private val updater: RuntimeDatasetMetadataCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FeatureSchemaDefinedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetMetadataReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetMetadataReprofiledEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
