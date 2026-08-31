package tech.medo.trainingorchestration.completetrainingjob

import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.completetrainingjob.CompleteTrainingJobCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class CompleteJobWhenTrainingStopCriteriaMetProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundCompletedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.roundNumber >= event.maxRounds) {
            commandGateway.send(CompleteTrainingJobCommand(trainingJobId = event.trainingJobId, finalRoundId = event.roundId, finalModelId = event.aggregatedModelId)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
