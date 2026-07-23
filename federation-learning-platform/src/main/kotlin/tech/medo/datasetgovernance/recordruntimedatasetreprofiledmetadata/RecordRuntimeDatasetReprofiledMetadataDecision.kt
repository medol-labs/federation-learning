package tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata.RecordRuntimeDatasetReprofiledMetadataCommand

import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent





@Component
class RecordRuntimeDatasetReprofiledMetadataDecision {
    fun decide(command: RecordRuntimeDatasetReprofiledMetadataCommand, state: RecordRuntimeDatasetReprofiledMetadataState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DatasetMetadataReportedEvent(metadataReportId = command.metadataReportId, datasetId = command.datasetId, organizationId = command.organizationId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, sampleCount = command.sampleCount, featureCount = command.featureCount, schemaCompatible = command.schemaCompatible, labelCompatible = command.labelCompatible, missingValueRate = command.missingValueRate, duplicateRate = command.duplicateRate, qualityScore = command.qualityScore, nonIidScore = command.nonIidScore, classBalanceScore = command.classBalanceScore)
        )
    }
}
