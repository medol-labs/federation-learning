package tech.medo.trainingorchestration.selecttrainingroundparticipants

import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.springframework.stereotype.Component

@Component
class SelectInitialRoundParticipantsWhenTrainingSubmittedProcessor(private val commandGateway: CommandGateway) {
    @DisallowReplay
    @EventHandler
    fun on(event: TrainingJobSubmittedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(SelectTrainingRoundParticipantsCommand(trainingJobId = event.trainingJobId)).resultMessage
}
