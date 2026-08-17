package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingConfiguredEvent
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ValidateAgentDatasetAccessWhenBindingConfiguredProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeDatasetBindingConfiguredEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ValidateAgentDatasetAccessCommand(runtimeDatasetBindingId = event.runtimeDatasetBindingId, datasetId = event.datasetId, organizationId = event.organizationId, featureSchemaId = event.featureSchemaId, datasetName = event.datasetName, runtimeId = event.runtimeId, dataSourceType = event.dataSourceType, host = event.host, port = event.port, url = event.url, databaseName = event.databaseName, schemaName = event.schemaName, tableName = event.tableName, filePath = event.filePath, objectBucket = event.objectBucket, objectPrefix = event.objectPrefix, dataFormat = event.dataFormat, credentialSecretName = event.credentialSecretName)).resultMessage
}
