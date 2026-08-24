package tech.medo.trainingorchestration.requestsecureaggregation

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.requestsecureaggregation.RequestSecureAggregationCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class RequestAggregationWhenEvaluatedUpdatesCompleteProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ModelUpdateSubmissionAcceptedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.acceptedModelUpdateCount >= event.minimumNodesPerRound) {
            commandGateway.send(RequestSecureAggregationCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, acceptedModelUpdateCount = event.acceptedModelUpdateCount, acceptedRuntimeIds = event.acceptedRuntimeIds, minimumNodesPerRound = event.minimumNodesPerRound)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
