package tech.medo.runtimeagentoperations.profileagentdataset

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetInput
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetService



@Component
class ProfileAgentDatasetCommandHandler(
    private val decision: ProfileAgentDatasetDecision,
    private val profileAgentDatasetService: ProfileAgentDatasetService
) {
    @CommandHandler
    fun handle(
        command: ProfileAgentDatasetCommand,
        eventAppender: EventAppender
    ) {
        val input = ProfileAgentDatasetInput(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, runtimeId = command.runtimeId)
        val portResult = profileAgentDatasetService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, portResult, now))
    }
}
