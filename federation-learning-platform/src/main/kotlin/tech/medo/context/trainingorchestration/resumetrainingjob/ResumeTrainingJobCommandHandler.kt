package tech.medo.trainingorchestration.resumetrainingjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.resumetrainingjob.ResumeTrainingJobCommand

import tech.medo.trainingorchestration.trainingjob.TrainingJobState



@Component
class ResumeTrainingJobCommandHandler(
    private val decision: ResumeTrainingJobDecision
) {
    @CommandHandler
    fun handle(
        command: ResumeTrainingJobCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
