package tech.medo.runtimeagentoperations.startroundexecution

import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class StartRoundExecutionWhenPlanAcceptedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ExecutionPlanAcceptedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(StartRoundExecutionCommand(executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = java.util.UUID.randomUUID() /* TODO: provide trainingRunConfigurationId */, roundId = java.util.UUID.randomUUID() /* TODO: provide roundId */, roundNumber = 0 /* TODO: provide roundNumber */, runtimeId = event.runtimeId, organizationId = java.util.UUID.randomUUID() /* TODO: provide organizationId */, featureSchemaId = java.util.UUID.randomUUID() /* TODO: provide featureSchemaId */, baseModelVersionId = java.util.UUID.randomUUID() /* TODO: provide baseModelVersionId */, runtimeEngineJobId = "" /* TODO: provide runtimeEngineJobId */)).resultMessage
}
