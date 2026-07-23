package tech.medo.modelrepository.registermodelartifact

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand




@Component
class RegisterModelArtifactCommandHandler(
    private val decision: RegisterModelArtifactDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterModelArtifactCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
