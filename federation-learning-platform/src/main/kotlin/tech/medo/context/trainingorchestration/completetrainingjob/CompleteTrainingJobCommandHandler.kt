package tech.medo.trainingorchestration.completetrainingjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.completetrainingjob.CompleteTrainingJobCommand

import tech.medo.trainingorchestration.trainingjob.TrainingJobState




@Component
class CompleteTrainingJobCommandHandler(
    private val decision: CompleteTrainingJobDecision
) {
    @CommandHandler
    fun handle(
        command: CompleteTrainingJobCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
