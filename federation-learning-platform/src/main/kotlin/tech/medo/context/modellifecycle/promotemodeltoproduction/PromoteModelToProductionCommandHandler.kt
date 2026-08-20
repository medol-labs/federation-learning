package tech.medo.modellifecycle.promotemodeltoproduction

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modellifecycle.promotemodeltoproduction.PromoteModelToProductionCommand

import tech.medo.modellifecycle.model.ModelState




@Component
class PromoteModelToProductionCommandHandler(
    private val decision: PromoteModelToProductionDecision
) {
    @CommandHandler
    fun handle(
        command: PromoteModelToProductionCommand,
        @InjectEntity(idProperty = "modelId") state: ModelState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
