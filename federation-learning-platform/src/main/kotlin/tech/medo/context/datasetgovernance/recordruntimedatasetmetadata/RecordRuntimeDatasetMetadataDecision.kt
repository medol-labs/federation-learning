package tech.medo.datasetgovernance.recordruntimedatasetmetadata

import tech.medo.datasetgovernance.recordruntimedatasetmetadata.RecordRuntimeDatasetMetadataCommand

import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent
import tech.medo.datasetgovernance.runtimedatasetmetadata.RuntimeDatasetMetadataState





interface RecordRuntimeDatasetMetadataDecision {
    fun decide(command: RecordRuntimeDatasetMetadataCommand): List<Any> {
        return listOf(
            DatasetMetadataReportedEvent(metadataReportId = command.metadataReportId, datasetId = command.datasetId, organizationId = command.organizationId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, sampleCount = command.sampleCount, featureCount = command.featureCount, schemaCompatible = command.schemaCompatible, labelCompatible = command.labelCompatible, missingValueRate = command.missingValueRate, duplicateRate = command.duplicateRate, qualityScore = command.qualityScore, nonIidScore = command.nonIidScore, classBalanceScore = command.classBalanceScore)
        )
    }
}
