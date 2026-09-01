package tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata

import tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata.RecordRuntimeDatasetReprofiledMetadataCommand

import tech.medo.datasetgovernance.events.DatasetMetadataReprofiledEvent
import tech.medo.datasetgovernance.runtimedatasetmetadata.RuntimeDatasetMetadataState





interface RecordRuntimeDatasetReprofiledMetadataDecision {
    fun decide(command: RecordRuntimeDatasetReprofiledMetadataCommand, state: RuntimeDatasetMetadataState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DatasetMetadataReprofiledEvent(runtimeDatasetBindingId = command.runtimeDatasetBindingId, metadataReportId = command.metadataReportId, datasetId = command.datasetId, organizationId = command.organizationId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName, sampleCount = command.sampleCount, featureCount = command.featureCount, schemaCompatible = command.schemaCompatible, labelCompatible = command.labelCompatible, missingValueRate = command.missingValueRate, duplicateRate = command.duplicateRate, qualityScore = command.qualityScore, nonIidScore = command.nonIidScore, classBalanceScore = command.classBalanceScore)
        )
    }
}
