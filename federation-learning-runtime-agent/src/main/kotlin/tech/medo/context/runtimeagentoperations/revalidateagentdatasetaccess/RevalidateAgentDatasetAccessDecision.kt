package tech.medo.runtimeagentoperations.revalidateagentdatasetaccess

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessCommand
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessResult
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidationFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationState


import tech.medo.runtimeagentoperations.domain.states.AgentDatasetAccessValidationStateEnum


@Component
class RevalidateAgentDatasetAccessDecision {
    fun decide(command: RevalidateAgentDatasetAccessCommand, state: AgentDatasetAccessValidationState, portResult: RevalidateAgentDatasetAccessResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == AgentDatasetAccessValidationStateEnum.CHECKED) {
            "RevalidateAgentDatasetAccess requires AgentDatasetAccessValidation to be Checked."
        }
        return when (portResult) {
                    is RevalidateAgentDatasetAccessResult.Succeeded -> listOf(AgentDatasetAccessRevalidatedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = portResult.runtimeDatasetBindingId, datasetId = portResult.datasetId, runtimeId = portResult.runtimeId, readable = portResult.readable, schemaReadable = portResult.schemaReadable, sampleBatchReadable = portResult.sampleBatchReadable))
                    is RevalidateAgentDatasetAccessResult.Rejected -> listOf(AgentDatasetAccessRevalidationFailedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = portResult.runtimeDatasetBindingId, datasetId = portResult.datasetId, runtimeId = portResult.runtimeId, failureReason = "Revalidate Agent Dataset Access rejected."))
                    is RevalidateAgentDatasetAccessResult.Unavailable -> listOf(AgentDatasetAccessRevalidationFailedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = java.util.UUID.randomUUID() /* TODO: provide datasetId */, runtimeId = java.util.UUID.randomUUID() /* TODO: provide runtimeId */, failureReason = portResult.failureReason))
                }
    }
}
