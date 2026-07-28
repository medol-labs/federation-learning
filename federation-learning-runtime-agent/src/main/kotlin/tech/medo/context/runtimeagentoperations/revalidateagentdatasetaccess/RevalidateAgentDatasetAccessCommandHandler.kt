package tech.medo.runtimeagentoperations.revalidateagentdatasetaccess

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessCommand
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessInput
import tech.medo.runtimeagentoperations.revalidateagentdatasetaccess.RevalidateAgentDatasetAccessService
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationState



@Component
class RevalidateAgentDatasetAccessCommandHandler(
    private val decision: RevalidateAgentDatasetAccessDecision,
    private val revalidateAgentDatasetAccessService: RevalidateAgentDatasetAccessService
) {
    @CommandHandler
    fun handle(
        command: RevalidateAgentDatasetAccessCommand,
        @InjectEntity(idProperty = "datasetAccessValidationId") state: AgentDatasetAccessValidationState,
        eventAppender: EventAppender
    ) {
        val input = RevalidateAgentDatasetAccessInput(datasetAccessValidationId = command.datasetAccessValidationId, runtimeDatasetBindingId = command.runtimeDatasetBindingId)
        val portResult = revalidateAgentDatasetAccessService.execute(input)
        val now = java.time.LocalDateTime.now()
        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}
