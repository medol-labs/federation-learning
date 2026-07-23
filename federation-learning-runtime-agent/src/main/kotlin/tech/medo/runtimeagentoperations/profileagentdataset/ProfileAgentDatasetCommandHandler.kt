package tech.medo.runtimeagentoperations.profileagentdataset

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand




@Component
class ProfileAgentDatasetCommandHandler(
    private val decision: ProfileAgentDatasetDecision
) {
    @CommandHandler
    fun handle(
        command: ProfileAgentDatasetCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
