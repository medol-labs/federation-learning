package tech.medo.modellifecycle.rollbackmodel

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modellifecycle.rollbackmodel.RollbackModelCommand

import tech.medo.modellifecycle.model.ModelState



@Component
class RollbackModelCommandHandler(
    private val decision: RollbackModelDecision
) {
    @CommandHandler
    fun handle(
        command: RollbackModelCommand,
        @InjectEntity(idProperty = "modelId") state: ModelState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
