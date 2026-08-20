package tech.medo.runtimegovernance.revokeruntimeidentity

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimegovernance.revokeruntimeidentity.RevokeRuntimeIdentityCommand

import tech.medo.runtimegovernance.runtimeidentity.RuntimeIdentityState




@Component
class RevokeRuntimeIdentityCommandHandler(
    private val decision: RevokeRuntimeIdentityDecision
) {
    @CommandHandler
    fun handle(
        command: RevokeRuntimeIdentityCommand,
        @InjectEntity(idProperty = "runtimeId") state: RuntimeIdentityState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
