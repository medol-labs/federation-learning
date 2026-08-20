package tech.medo.modellifecycle.approvemodel

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modellifecycle.approvemodel.ApproveModelCommand

import tech.medo.modellifecycle.model.ModelState




@Component
class ApproveModelCommandHandler(
    private val decision: ApproveModelDecision
) {
    @CommandHandler
    fun handle(
        command: ApproveModelCommand,
        @InjectEntity(idProperty = "modelId") state: ModelState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
