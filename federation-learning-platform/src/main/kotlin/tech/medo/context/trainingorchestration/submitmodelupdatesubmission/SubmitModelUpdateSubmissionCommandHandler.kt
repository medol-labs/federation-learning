package tech.medo.trainingorchestration.submitmodelupdatesubmission

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.submitmodelupdatesubmission.SubmitModelUpdateSubmissionCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState



@Component
class SubmitModelUpdateSubmissionCommandHandler(
    private val decision: SubmitModelUpdateSubmissionDecision
) {
    @CommandHandler
    fun handle(
        command: SubmitModelUpdateSubmissionCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
