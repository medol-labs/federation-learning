package tech.medo.trainingorchestration.completetraininground

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState




@Component
class CompleteTrainingRoundCommandHandler(
    private val decision: CompleteTrainingRoundDecision
) {
    @CommandHandler
    fun handle(
        command: CompleteTrainingRoundCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
