package tech.medo.runtimeagentoperations.rejectdatasetfortraining

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.rejectdatasetfortraining.RejectDatasetForTrainingCommand

import tech.medo.runtimeagentoperations.dataset.DatasetState




@Component
class RejectDatasetForTrainingCommandHandler(
    private val decision: RejectDatasetForTrainingDecision
) {
    @CommandHandler
    fun handle(
        command: RejectDatasetForTrainingCommand,
        @InjectEntity(idProperty = "selection") state: DatasetState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
