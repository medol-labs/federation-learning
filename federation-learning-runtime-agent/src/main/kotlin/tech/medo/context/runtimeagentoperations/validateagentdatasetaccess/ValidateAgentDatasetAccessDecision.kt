package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessCommand
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessResult
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationState





@Component
class ValidateAgentDatasetAccessDecision {
    fun decide(command: ValidateAgentDatasetAccessCommand, portResult: ValidateAgentDatasetAccessResult, now: java.time.LocalDateTime): List<Any> {
        return when (portResult) {
                    is ValidateAgentDatasetAccessResult.Succeeded -> listOf(AgentDatasetAccessValidatedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, runtimeId = command.runtimeId, readable = portResult.readable, schemaReadable = portResult.schemaReadable, sampleBatchReadable = portResult.sampleBatchReadable))
                    is ValidateAgentDatasetAccessResult.Rejected -> listOf(AgentDatasetAccessValidationFailedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, runtimeId = command.runtimeId, failureReason = portResult.failureReason))
                    is ValidateAgentDatasetAccessResult.Unavailable -> listOf(AgentDatasetAccessValidationFailedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, runtimeId = command.runtimeId, failureReason = portResult.failureReason))
                }
    }
}
