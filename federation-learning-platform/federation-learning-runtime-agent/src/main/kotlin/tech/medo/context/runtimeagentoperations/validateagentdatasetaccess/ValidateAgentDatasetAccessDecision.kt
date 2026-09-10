package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessCommand

import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessResult
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationState





interface ValidateAgentDatasetAccessDecision {
    fun decide(command: ValidateAgentDatasetAccessCommand, portResult: ValidateAgentDatasetAccessResult, now: java.time.LocalDateTime): List<Any> {
        return when (portResult) {
                    is ValidateAgentDatasetAccessResult.Succeeded -> listOf(
            AgentDatasetAccessValidatedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, organizationName = command.organizationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, readable = portResult.readable, schemaReadable = portResult.schemaReadable, sampleBatchReadable = portResult.sampleBatchReadable)
            )
                    is ValidateAgentDatasetAccessResult.Rejected -> listOf(AgentDatasetAccessValidationFailedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, organizationName = command.organizationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, failureReason = portResult.failureReason))
                    is ValidateAgentDatasetAccessResult.Unavailable -> listOf(AgentDatasetAccessValidationFailedEvent(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, organizationName = command.organizationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, failureReason = portResult.failureReason))
                }
    }
}
