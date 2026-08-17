package tech.medo.infrastructure.secondary.datasetgovernance.runtimedatasetmetadatacatalog

import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReprofiledEvent
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModelProjection
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModelRepository
import tech.medo.shared.application.metadata.ProjectionMetadata
import java.time.LocalDateTime

@Component
class RuntimeDatasetMetadataCatalogEventHandler(
    private val repository: RuntimeDatasetMetadataCatalogReadModelRepository
) {
    @EventHandler
    fun on(event: DatasetMetadataReportedEvent, message: EventMessage) {
        val entity = repository.findProjectionById(event.metadataReportId)
            ?: RuntimeDatasetMetadataCatalogReadModelProjection().apply {
                metadataReportId = event.metadataReportId
            }
        entity.applyReportedMetadata(event)
        ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(event: DatasetMetadataReprofiledEvent, message: EventMessage) {
        val entity = repository.findProjectionById(event.metadataReportId)
            ?: RuntimeDatasetMetadataCatalogReadModelProjection().apply {
                metadataReportId = event.metadataReportId
            }
        entity.applyReprofiledMetadata(event)
        ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun RuntimeDatasetMetadataCatalogReadModelProjection.applyReportedMetadata(event: DatasetMetadataReportedEvent) {
        datasetId = event.datasetId
        organizationId = event.organizationId
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
        datasetName = event.optionalString("datasetName") ?: datasetName
        sampleCount = event.sampleCount
        featureCount = event.featureCount
        schemaCompatible = event.schemaCompatible
        labelCompatible = event.labelCompatible
        missingValueRate = event.missingValueRate
        duplicateRate = event.duplicateRate
        qualityScore = event.qualityScore
        nonIidScore = event.nonIidScore
        classBalanceScore = event.classBalanceScore
        profilingStatus = "REPORTED"
        failureReason = null
        profiledAt = LocalDateTime.now()
    }

    private fun RuntimeDatasetMetadataCatalogReadModelProjection.applyReprofiledMetadata(event: DatasetMetadataReprofiledEvent) {
        datasetId = event.datasetId
        organizationId = event.organizationId
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
        datasetName = event.optionalString("datasetName") ?: datasetName
        sampleCount = event.sampleCount
        featureCount = event.featureCount
        schemaCompatible = event.schemaCompatible
        labelCompatible = event.labelCompatible
        missingValueRate = event.missingValueRate
        duplicateRate = event.duplicateRate
        qualityScore = event.qualityScore
        nonIidScore = event.nonIidScore
        classBalanceScore = event.classBalanceScore
        profilingStatus = "REPROFILED"
        failureReason = null
        profiledAt = LocalDateTime.now()
    }

    private fun Any.optionalString(propertyName: String): String? =
        runCatching {
            javaClass.getMethod("get${propertyName.replaceFirstChar { it.uppercaseChar() }}").invoke(this) as? String
        }.getOrNull()
}
