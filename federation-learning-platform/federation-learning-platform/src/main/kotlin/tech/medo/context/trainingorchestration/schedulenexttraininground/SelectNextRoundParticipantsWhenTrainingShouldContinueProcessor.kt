package tech.medo.trainingorchestration.schedulenexttraininground

import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class SelectNextRoundParticipantsWhenTrainingShouldContinueProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundCompletedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.roundNumber < event.maxRounds) {
            commandGateway.send(SelectTrainingRoundParticipantsCommand(trainingJobId = event.trainingJobId)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
