package tech.medo.trainingorchestration.completetrainingjob

import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.completetrainingjob.CompleteTrainingJobCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class CompleteJobWhenRoundBudgetExhaustedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundCompletedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(CompleteTrainingJobCommand(trainingJobId = event.trainingJobId, finalRoundId = java.util.UUID.randomUUID() /* TODO: provide finalRoundId */, finalModelId = java.util.UUID.randomUUID() /* TODO: provide finalModelId */, stopReason = "" /* TODO: provide stopReason */)).resultMessage
}
