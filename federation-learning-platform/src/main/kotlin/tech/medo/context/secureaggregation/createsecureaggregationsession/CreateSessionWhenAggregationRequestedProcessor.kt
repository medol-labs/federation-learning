package tech.medo.secureaggregation.createsecureaggregationsession

import tech.medo.trainingorchestration.events.SecureAggregationRequestedEvent
import tech.medo.secureaggregation.createsecureaggregationsession.CreateSecureAggregationSessionCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class CreateSessionWhenAggregationRequestedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: SecureAggregationRequestedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(CreateSecureAggregationSessionCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, requiredParticipantCount = event.acceptedModelUpdateCount, acceptedRuntimeIds = event.acceptedRuntimeIds)).resultMessage
}
