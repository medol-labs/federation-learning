package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.runtimedatasetbinding.RuntimeDatasetBindingSelection
import java.util.UUID;

import tech.medo.runtimeagentoperations.runtimedatasetbinding.RuntimeDatasetBindingRuntimeIdDatasetIdSelection

@Command
data class ConfigureRuntimeDatasetBindingCommand(
    val runtimeDatasetBindingId: UUID = java.util.UUID.randomUUID(),
    val datasetId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val organizationName: String?,
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
    val selection: RuntimeDatasetBindingSelection = RuntimeDatasetBindingSelection(datasetId = datasetId, runtimeId = runtimeId)

    val runtimeDatasetBindingRuntimeIdDatasetIdSelection: RuntimeDatasetBindingRuntimeIdDatasetIdSelection = RuntimeDatasetBindingRuntimeIdDatasetIdSelection(normalizedRuntimeId = runtimeId.toString().trim().lowercase(), normalizedDatasetId = datasetId.toString().trim().lowercase())
}
