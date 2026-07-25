package tech.medo.runtimeprovisioning.registerruntimeinfrastructure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.registerruntimeinfrastructure.RegisterRuntimeInfrastructureCommand




@Component
class RegisterRuntimeInfrastructureCommandHandler(
    private val decision: RegisterRuntimeInfrastructureDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterRuntimeInfrastructureCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
