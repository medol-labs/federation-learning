package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState




@Component
class EvaluateModelUpdateSubmissionCommandHandler(
    private val decision: EvaluateModelUpdateSubmissionDecision
) {
    @CommandHandler
    fun handle(
        command: EvaluateModelUpdateSubmissionCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
