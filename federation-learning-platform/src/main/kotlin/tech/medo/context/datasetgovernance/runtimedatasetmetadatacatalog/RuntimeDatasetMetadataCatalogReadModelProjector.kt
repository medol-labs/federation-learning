package tech.medo.datasetgovernance.runtimedatasetmetadatacatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent



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

}
