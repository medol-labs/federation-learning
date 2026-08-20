package tech.medo.modellifecycle.registercandidatemodel

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.modellifecycle.registercandidatemodel.RegisterCandidateModelCommand





@Component
class RegisterCandidateModelCommandHandler(
    private val decision: RegisterCandidateModelDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterCandidateModelCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
