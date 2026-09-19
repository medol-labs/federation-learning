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
        val input = ValidateAgentDatasetAccessInput(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, organizationName = command.organizationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, filePath = command.filePath, dataFormat = command.dataFormat)
        val portResult = validateAgentDatasetAccessService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, portResult, now))
    }
}
