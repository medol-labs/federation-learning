package tech.medo.trainingorchestration.pausetrainingjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.pausetrainingjob.PauseTrainingJobCommand

import tech.medo.trainingorchestration.trainingjob.TrainingJobState



@Component
class PauseTrainingJobCommandHandler(
    private val decision: PauseTrainingJobDecision
) {
    @CommandHandler
    fun handle(
        command: PauseTrainingJobCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
