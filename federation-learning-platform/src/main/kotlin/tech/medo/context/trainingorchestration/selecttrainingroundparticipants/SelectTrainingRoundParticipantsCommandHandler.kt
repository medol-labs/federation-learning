package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand




@Component
class SelectTrainingRoundParticipantsCommandHandler(
    private val decision: SelectTrainingRoundParticipantsDecision
) {
    @CommandHandler
    fun handle(
        command: SelectTrainingRoundParticipantsCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
