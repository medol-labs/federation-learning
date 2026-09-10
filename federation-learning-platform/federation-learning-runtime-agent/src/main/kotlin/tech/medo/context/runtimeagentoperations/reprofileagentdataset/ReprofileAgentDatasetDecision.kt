package tech.medo.runtimeagentoperations.reprofileagentdataset

import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetCommand

import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetResult
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofiledEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofilingFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetprofile.AgentDatasetProfileState


import tech.medo.runtimeagentoperations.domain.states.AgentDatasetProfileStateEnum


interface ReprofileAgentDatasetDecision {
    fun decide(command: ReprofileAgentDatasetCommand, state: AgentDatasetProfileState, portResult: ReprofileAgentDatasetResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == AgentDatasetProfileStateEnum.REPORTED) {
            "ReprofileAgentDataset requires AgentDatasetProfile to be Reported."
        }
        return when (portResult) {
                    is ReprofileAgentDatasetResult.Succeeded -> listOf(AgentDatasetReprofiledEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = requireNotNull(state.datasetId) { "datasetId is required from state." }, organizationId = requireNotNull(state.organizationId) { "organizationId is required from state." }, organizationName = state.organizationName, runtimeId = requireNotNull(state.runtimeId) { "runtimeId is required from state." }, runtimeName = state.runtimeName, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, featureDomain = state.featureDomain, featureSchemaVersion = state.featureSchemaVersion, datasetName = requireNotNull(state.datasetName) { "datasetName is required from state." }, sampleCount = portResult.sampleCount, featureCount = portResult.featureCount, schemaCompatible = portResult.schemaCompatible, labelCompatible = portResult.labelCompatible, missingValueRate = portResult.missingValueRate, duplicateRate = portResult.duplicateRate, qualityScore = portResult.qualityScore, nonIidScore = portResult.nonIidScore, classBalanceScore = portResult.classBalanceScore))
                    is ReprofileAgentDatasetResult.Rejected -> listOf(AgentDatasetReprofilingFailedEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = requireNotNull(state.datasetId) { "datasetId is required from state." }, organizationId = requireNotNull(state.organizationId) { "organizationId is required from state." }, organizationName = state.organizationName, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, featureDomain = state.featureDomain, featureSchemaVersion = state.featureSchemaVersion, datasetName = requireNotNull(state.datasetName) { "datasetName is required from state." }, runtimeId = requireNotNull(state.runtimeId) { "runtimeId is required from state." }, runtimeName = state.runtimeName, failureReason = portResult.failureReason))
                    is ReprofileAgentDatasetResult.Unavailable -> listOf(AgentDatasetReprofilingFailedEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = requireNotNull(state.datasetId) { "datasetId is required from state." }, organizationId = requireNotNull(state.organizationId) { "organizationId is required from state." }, organizationName = state.organizationName, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, featureDomain = state.featureDomain, featureSchemaVersion = state.featureSchemaVersion, datasetName = requireNotNull(state.datasetName) { "datasetName is required from state." }, runtimeId = requireNotNull(state.runtimeId) { "runtimeId is required from state." }, runtimeName = state.runtimeName, failureReason = portResult.failureReason))
                }
    }
}
