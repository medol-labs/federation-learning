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
    fun on(event: ModelUpdateSubmissionAcceptedEvent): java.util.concurrent.CompletableFuture<RequestSecureAggregationCommand> =
        commandGateway.send(RequestSecureAggregationCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = java.util.UUID.randomUUID() /* TODO: provide featureSchemaId */, roundId = event.roundId, acceptedModelUpdateCount = 0 /* TODO: provide acceptedModelUpdateCount */, acceptedRuntimeIds = emptyList() /* TODO: provide acceptedRuntimeIds */, minimumNodesPerRound = 0 /* TODO: provide minimumNodesPerRound */)).resultMessage.thenApply { it.payload() as RequestSecureAggregationCommand }
}
