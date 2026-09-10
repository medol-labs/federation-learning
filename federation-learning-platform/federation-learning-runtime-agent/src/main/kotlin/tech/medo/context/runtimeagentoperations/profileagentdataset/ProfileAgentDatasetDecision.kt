package tech.medo.runtimeagentoperations.profileagentdataset

import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand

import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetResult
import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetProfilingFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetprofile.AgentDatasetProfileState





interface ProfileAgentDatasetDecision {
    fun decide(command: ProfileAgentDatasetCommand, portResult: ProfileAgentDatasetResult, now: java.time.LocalDateTime): List<Any> {
        return when (portResult) {
                    is ProfileAgentDatasetResult.Succeeded -> listOf(AgentDatasetMetadataReportedEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, sampleCount = portResult.sampleCount, featureCount = portResult.featureCount, schemaCompatible = portResult.schemaCompatible, labelCompatible = portResult.labelCompatible, missingValueRate = portResult.missingValueRate, duplicateRate = portResult.duplicateRate, qualityScore = portResult.qualityScore, nonIidScore = portResult.nonIidScore, classBalanceScore = portResult.classBalanceScore))
                    is ProfileAgentDatasetResult.Rejected -> listOf(AgentDatasetProfilingFailedEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, organizationName = command.organizationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, failureReason = portResult.failureReason))
                    is ProfileAgentDatasetResult.Unavailable -> listOf(AgentDatasetProfilingFailedEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, organizationName = command.organizationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, failureReason = portResult.failureReason))
                }
    }
}
