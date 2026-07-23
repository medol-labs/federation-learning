package tech.medo.runtimeagentoperations.validateagentdatasetaccess

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessCommand




@Component
class ValidateAgentDatasetAccessCommandHandler(
    private val decision: ValidateAgentDatasetAccessDecision
) {
    @CommandHandler
    fun handle(
        command: ValidateAgentDatasetAccessCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
