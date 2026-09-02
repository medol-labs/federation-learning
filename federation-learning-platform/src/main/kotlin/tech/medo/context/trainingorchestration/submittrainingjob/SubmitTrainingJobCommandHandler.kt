package tech.medo.trainingorchestration.submittrainingjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.submittrainingjob.SubmitTrainingJobCommand

import tech.medo.trainingorchestration.trainingjob.TrainingJobState



@Component
class SubmitTrainingJobCommandHandler(
    private val decision: SubmitTrainingJobDecision
) {
    @CommandHandler
    fun handle(
        command: SubmitTrainingJobCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
