package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationSelection
import java.util.UUID;


@Command
data class ValidateAgentDatasetAccessCommand(
    val datasetAccessValidationId: UUID = java.util.UUID.randomUUID(),
    val runtimeDatasetBindingId: UUID,
    val datasetId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String,
    val runtimeId: UUID,
    val runtimeName: String?,
    val filePath: String,
    val dataFormat: String
) {
    @TargetEntityId
    val selection: AgentDatasetAccessValidationSelection = AgentDatasetAccessValidationSelection(datasetAccessValidationId = datasetAccessValidationId)

}
