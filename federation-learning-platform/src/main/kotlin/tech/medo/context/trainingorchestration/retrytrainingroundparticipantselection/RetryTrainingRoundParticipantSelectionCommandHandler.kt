package tech.medo.trainingorchestration.retrytrainingroundparticipantselection

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.retrytrainingroundparticipantselection.RetryTrainingRoundParticipantSelectionCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState



@Component
class RetryTrainingRoundParticipantSelectionCommandHandler(
    private val decision: RetryTrainingRoundParticipantSelectionDecision
) {
    @CommandHandler
    fun handle(
        command: RetryTrainingRoundParticipantSelectionCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
