package tech.medo.modellifecycle.retiremodel

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modellifecycle.retiremodel.RetireModelCommand

import tech.medo.modellifecycle.model.ModelState



@Component
class RetireModelCommandHandler(
    private val decision: RetireModelDecision
) {
    @CommandHandler
    fun handle(
        command: RetireModelCommand,
        @InjectEntity(idProperty = "modelId") state: ModelState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
