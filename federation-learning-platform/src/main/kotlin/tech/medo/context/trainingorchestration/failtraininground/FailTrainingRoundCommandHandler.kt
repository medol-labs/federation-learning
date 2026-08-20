package tech.medo.trainingorchestration.failtraininground

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.failtraininground.FailTrainingRoundCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState




@Component
class FailTrainingRoundCommandHandler(
    private val decision: FailTrainingRoundDecision
) {
    @CommandHandler
    fun handle(
        command: FailTrainingRoundCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
