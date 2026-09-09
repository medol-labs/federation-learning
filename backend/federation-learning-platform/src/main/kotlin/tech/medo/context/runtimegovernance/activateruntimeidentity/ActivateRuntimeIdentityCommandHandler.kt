package tech.medo.runtimegovernance.activateruntimeidentity

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimegovernance.activateruntimeidentity.ActivateRuntimeIdentityCommand





@Component
class ActivateRuntimeIdentityCommandHandler(
    private val decision: ActivateRuntimeIdentityDecision
) {
    @CommandHandler
    fun handle(
        command: ActivateRuntimeIdentityCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
