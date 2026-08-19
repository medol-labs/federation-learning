package tech.medo.trainingorchestration.retrytrainingroundparticipantselection

import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionRetryRequestedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class SelectTrainingRoundParticipantsWhenRetryRequestedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundParticipantSelectionRetryRequestedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(SelectTrainingRoundParticipantsCommand(trainingJobId = event.trainingJobId)).resultMessage
}
