package tech.medo.modellifecycle.retiremodelversion

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modellifecycle.retiremodelversion.RetireModelVersionCommand

import tech.medo.modellifecycle.modelversion.ModelVersionState



@Component
class RetireModelVersionCommandHandler(
    private val decision: RetireModelVersionDecision
) {
    @CommandHandler
    fun handle(
        command: RetireModelVersionCommand,
        @InjectEntity(idProperty = "modelVersionId") state: ModelVersionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
