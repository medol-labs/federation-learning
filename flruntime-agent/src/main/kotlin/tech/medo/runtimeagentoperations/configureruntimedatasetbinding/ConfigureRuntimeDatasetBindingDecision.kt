package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.configureruntimedatasetbinding.ConfigureRuntimeDatasetBindingCommand

import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingConfiguredEvent
import tech.medo.runtimeagentoperations.runtimedatasetbinding.RuntimeDatasetBindingState





@Component
class ConfigureRuntimeDatasetBindingDecision {
    fun decide(command: ConfigureRuntimeDatasetBindingCommand): List<Any> {
        return listOf(
            RuntimeDatasetBindingConfiguredEvent(runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, runtimeId = command.runtimeId, dataSourceType = command.dataSourceType, host = command.host, port = command.port, url = command.url, databaseName = command.databaseName, schemaName = command.schemaName, tableName = command.tableName, filePath = command.filePath, objectBucket = command.objectBucket, objectPrefix = command.objectPrefix, dataFormat = command.dataFormat, credentialSecretName = command.credentialSecretName)
        )
    }
}
