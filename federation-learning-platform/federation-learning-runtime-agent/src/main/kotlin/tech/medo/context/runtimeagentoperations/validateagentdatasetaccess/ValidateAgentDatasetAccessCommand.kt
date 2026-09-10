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
    val dataSourceType: String,
    val host: String?,
    val port: Int?,
    val url: String?,
    val databaseName: String?,
    val schemaName: String?,
    val tableName: String?,
    val filePath: String?,
    val objectBucket: String?,
    val objectPrefix: String?,
    val dataFormat: String,
    val credentialSecretName: String?
) {
    @TargetEntityId
    val selection: AgentDatasetAccessValidationSelection = AgentDatasetAccessValidationSelection(datasetAccessValidationId = datasetAccessValidationId)

}
