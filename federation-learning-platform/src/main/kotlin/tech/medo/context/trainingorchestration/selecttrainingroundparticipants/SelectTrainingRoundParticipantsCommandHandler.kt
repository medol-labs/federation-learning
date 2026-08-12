package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand




@Component
class SelectTrainingRoundParticipantsCommandHandler(
    private val decision: SelectTrainingRoundParticipantsDecision,
    private val selectTrainingRoundParticipantsService: SelectTrainingRoundParticipantsService
) {
    @CommandHandler
    fun handle(
        command: SelectTrainingRoundParticipantsCommand,
        eventAppender: EventAppender
    ) {
        val input = SelectTrainingRoundParticipantsInput(trainingJobId = command.trainingJobId)
        val portResult = selectTrainingRoundParticipantsService.execute(input)

        eventAppender.append(decision.decide(command, portResult))
    }
}
