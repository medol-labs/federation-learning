package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessCommand

import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationState





@Component
class ValidateAgentDatasetAccessDecision {
    fun decide(command: ValidateAgentDatasetAccessCommand): List<Any> {
        return listOf(
            AgentDatasetAccessValidatedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = java.util.UUID.randomUUID() /* TODO: derive value */, runtimeId = java.util.UUID.randomUUID() /* TODO: derive value */, readable = false /* TODO: derive value */, schemaReadable = false /* TODO: derive value */, sampleBatchReadable = false /* TODO: derive value */)
        )
    }
}
