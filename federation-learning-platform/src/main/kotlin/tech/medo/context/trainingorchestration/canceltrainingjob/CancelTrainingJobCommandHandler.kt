package tech.medo.trainingorchestration.canceltrainingjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.canceltrainingjob.CancelTrainingJobCommand

import tech.medo.trainingorchestration.trainingjob.TrainingJobState




@Component
class CancelTrainingJobCommandHandler(
    private val decision: CancelTrainingJobDecision
) {
    @CommandHandler
    fun handle(
        command: CancelTrainingJobCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
