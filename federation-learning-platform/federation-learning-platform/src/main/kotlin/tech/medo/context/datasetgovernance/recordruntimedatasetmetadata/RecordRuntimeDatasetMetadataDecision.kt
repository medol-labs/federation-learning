package tech.medo.datasetgovernance.recordruntimedatasetmetadata

import tech.medo.datasetgovernance.recordruntimedatasetmetadata.RecordRuntimeDatasetMetadataCommand


import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent
import tech.medo.datasetgovernance.runtimedatasetmetadata.RuntimeDatasetMetadataState





interface RecordRuntimeDatasetMetadataDecision {
    fun decide(command: RecordRuntimeDatasetMetadataCommand): List<Any> {
        return listOf(
            DatasetMetadataReportedEvent(runtimeDatasetBindingId = command.runtimeDatasetBindingId, metadataReportId = command.metadataReportId, datasetId = command.datasetId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, sampleCount = command.sampleCount, featureCount = command.featureCount, schemaCompatible = command.schemaCompatible, labelCompatible = command.labelCompatible, missingValueRate = command.missingValueRate, duplicateRate = command.duplicateRate, qualityScore = command.qualityScore, nonIidScore = command.nonIidScore, classBalanceScore = command.classBalanceScore)
        )
    }
}
