package tech.medo.runtimeagentoperations.revalidateagentdatasetaccess

import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessCommand

import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessResult
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidationFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationState


import tech.medo.runtimeagentoperations.domain.states.AgentDatasetAccessValidationStateEnum


interface RevalidateAgentDatasetAccessDecision {
    fun decide(command: RevalidateAgentDatasetAccessCommand, state: AgentDatasetAccessValidationState, portResult: RevalidateAgentDatasetAccessResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == AgentDatasetAccessValidationStateEnum.CHECKED) {
            "RevalidateAgentDatasetAccess requires AgentDatasetAccessValidation to be Checked."
        }
        return when (portResult) {
                    is RevalidateAgentDatasetAccessResult.Succeeded -> listOf(AgentDatasetAccessRevalidatedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = requireNotNull(state.datasetId) { "datasetId is required from state." }, organizationId = requireNotNull(state.organizationId) { "organizationId is required from state." }, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, datasetName = requireNotNull(state.datasetName) { "datasetName is required from state." }, runtimeId = requireNotNull(state.runtimeId) { "runtimeId is required from state." }, readable = portResult.readable, schemaReadable = portResult.schemaReadable, sampleBatchReadable = portResult.sampleBatchReadable))
                    is RevalidateAgentDatasetAccessResult.Rejected -> listOf(AgentDatasetAccessRevalidationFailedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = requireNotNull(state.datasetId) { "datasetId is required from state." }, organizationId = requireNotNull(state.organizationId) { "organizationId is required from state." }, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, datasetName = requireNotNull(state.datasetName) { "datasetName is required from state." }, runtimeId = requireNotNull(state.runtimeId) { "runtimeId is required from state." }, failureReason = portResult.failureReason))
                    is RevalidateAgentDatasetAccessResult.Unavailable -> listOf(AgentDatasetAccessRevalidationFailedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = requireNotNull(state.datasetId) { "datasetId is required from state." }, organizationId = requireNotNull(state.organizationId) { "organizationId is required from state." }, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, datasetName = requireNotNull(state.datasetName) { "datasetName is required from state." }, runtimeId = requireNotNull(state.runtimeId) { "runtimeId is required from state." }, failureReason = portResult.failureReason))
                }
    }
}
