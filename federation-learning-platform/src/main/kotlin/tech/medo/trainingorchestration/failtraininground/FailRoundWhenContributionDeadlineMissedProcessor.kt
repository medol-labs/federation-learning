package tech.medo.trainingorchestration.failtraininground

import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.failtraininground.FailTrainingRoundCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class FailRoundWhenContributionDeadlineMissedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundStartedEvent): java.util.concurrent.CompletableFuture<FailTrainingRoundCommand> =
        commandGateway.send(FailTrainingRoundCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, selectedRuntimeCount = event.selectedRuntimeCount, minimumNodesPerRound = event.minimumNodesPerRound, failureReason = "" /* TODO: provide failureReason */)).resultMessage.thenApply { it.payload() as FailTrainingRoundCommand }
}
