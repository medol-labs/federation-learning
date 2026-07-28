package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessCommand
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessInput
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessService



@Component
class ValidateAgentDatasetAccessCommandHandler(
    private val decision: ValidateAgentDatasetAccessDecision,
    private val validateAgentDatasetAccessService: ValidateAgentDatasetAccessService
) {
    @CommandHandler
    fun handle(
        command: ValidateAgentDatasetAccessCommand,
        eventAppender: EventAppender
    ) {
        val input = ValidateAgentDatasetAccessInput(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, runtimeId = command.runtimeId, dataSourceType = command.dataSourceType, host = command.host, port = command.port, url = command.url, databaseName = command.databaseName, schemaName = command.schemaName, tableName = command.tableName, filePath = command.filePath, objectBucket = command.objectBucket, objectPrefix = command.objectPrefix, dataFormat = command.dataFormat, credentialSecretName = command.credentialSecretName)
        val portResult = validateAgentDatasetAccessService.execute(input)
        val now = java.time.LocalDateTime.now()
        eventAppender.append(decision.decide(command, portResult, now))
    }
}
