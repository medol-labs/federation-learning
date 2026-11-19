package tech.medo.trainingorchestration.recoverhistoricaltraininground

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.recoverhistoricaltraininground.RecoverHistoricalTrainingRoundCommand



import tech.medo.trainingorchestration.traininground.TrainingRoundState



@Component
class RecoverHistoricalTrainingRoundCommandHandler(
    private val decision: RecoverHistoricalTrainingRoundDecision
) {
    @CommandHandler
    fun handle(
        command: RecoverHistoricalTrainingRoundCommand,
        @InjectEntity(idProperty = "roundId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
