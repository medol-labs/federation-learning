package tech.medo.trainingorchestration.failtraininground

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionRejectedEvent
import tech.medo.trainingorchestration.failtraininground.FailTrainingRoundCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class FailRoundWhenRejectedUpdatesBreakQuorumProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ModelUpdateSubmissionRejectedEvent): java.util.concurrent.CompletableFuture<*> =
        if (false) {
            commandGateway.send(FailTrainingRoundCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = java.util.UUID.randomUUID() /* TODO: provide featureSchemaId */, roundId = event.roundId, selectedRuntimeCount = 0 /* TODO: provide selectedRuntimeCount */, minimumNodesPerRound = 0 /* TODO: provide minimumNodesPerRound */, failureReason = "" /* TODO: provide failureReason */)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
