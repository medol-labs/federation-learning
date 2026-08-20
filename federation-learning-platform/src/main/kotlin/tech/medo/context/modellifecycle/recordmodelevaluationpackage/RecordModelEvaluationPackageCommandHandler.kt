package tech.medo.modellifecycle.recordmodelevaluationpackage

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modellifecycle.recordmodelevaluationpackage.RecordModelEvaluationPackageCommand

import tech.medo.modellifecycle.model.ModelState




@Component
class RecordModelEvaluationPackageCommandHandler(
    private val decision: RecordModelEvaluationPackageDecision
) {
    @CommandHandler
    fun handle(
        command: RecordModelEvaluationPackageCommand,
        @InjectEntity(idProperty = "modelId") state: ModelState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
