package tech.medo.modellifecycle.rollbackmodelversion

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modellifecycle.rollbackmodelversion.RollbackModelVersionCommand

import tech.medo.modellifecycle.modelversion.ModelVersionState



@Component
class RollbackModelVersionCommandHandler(
    private val decision: RollbackModelVersionDecision
) {
    @CommandHandler
    fun handle(
        command: RollbackModelVersionCommand,
        @InjectEntity(idProperty = "modelVersionId") state: ModelVersionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
