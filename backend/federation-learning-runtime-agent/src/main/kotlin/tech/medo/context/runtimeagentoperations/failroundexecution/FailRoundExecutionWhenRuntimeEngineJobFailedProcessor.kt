package tech.medo.runtimeagentoperations.failroundexecution

import tech.medo.runtimeagentoperations.events.RuntimeEngineJobObservedEvent
import tech.medo.runtimeagentoperations.failroundexecution.FailRoundExecutionCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class FailRoundExecutionWhenRuntimeEngineJobFailedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeEngineJobObservedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.observedStatus == "FAILED") {
            commandGateway.send(FailRoundExecutionCommand(roundExecutionId = event.roundExecutionId, executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, featureSchemaId = event.featureSchemaId, runtimeEngineJobId = event.runtimeEngineJobId, failureReason = event.failureReason)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
