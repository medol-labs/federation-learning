package tech.medo.datasetgovernance.recordruntimedatasetmetadata

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.recordruntimedatasetmetadata.RecordRuntimeDatasetMetadataCommand

import tech.medo.datasetgovernance.events.DatasetMetadataReprofiledEvent
import tech.medo.datasetgovernance.dataset.DatasetState





@Component
class RecordRuntimeDatasetMetadataDecision {
    fun decide(command: RecordRuntimeDatasetMetadataCommand, state: DatasetState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DatasetMetadataReprofiledEvent(metadataReportId = command.metadataReportId, datasetId = command.datasetId, organizationId = command.organizationId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, sampleCount = command.sampleCount, featureCount = command.featureCount, schemaCompatible = command.schemaCompatible, labelCompatible = command.labelCompatible, missingValueRate = command.missingValueRate, duplicateRate = command.duplicateRate, qualityScore = command.qualityScore, nonIidScore = command.nonIidScore, classBalanceScore = command.classBalanceScore)
        )
    }
}
