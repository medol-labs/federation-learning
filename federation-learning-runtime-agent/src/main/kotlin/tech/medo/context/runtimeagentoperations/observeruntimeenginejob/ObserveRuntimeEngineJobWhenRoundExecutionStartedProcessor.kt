package tech.medo.runtimeagentoperations.observeruntimeenginejob

import tech.medo.runtimeagentoperations.events.RoundExecutionStartedEvent
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ObserveRuntimeEngineJobWhenRoundExecutionStartedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RoundExecutionStartedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ObserveRuntimeEngineJobCommand(roundExecutionId = event.roundExecutionId, executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, featureSchemaId = event.featureSchemaId, runtimeEngineJobId = event.runtimeEngineJobId)).resultMessage
}
