package tech.medo.trainingorchestration.registerruntimeengineprofile

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.registerruntimeengineprofile.RegisterRuntimeEngineProfileCommand





@Component
class RegisterRuntimeEngineProfileCommandHandler(
    private val decision: RegisterRuntimeEngineProfileDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterRuntimeEngineProfileCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
