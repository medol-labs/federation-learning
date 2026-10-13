package tech.medo.runtimemonitoring.markruntimeagentrecovered

import tech.medo.runtimemonitoring.events.RuntimeTelemetryRecordedEvent
import tech.medo.runtimemonitoring.markruntimeagentrecovered.MarkRuntimeAgentRecoveredCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-monitoring-mark-runtime-agent-recovered")
@Component
class MarkRecoveredRuntimeAgentFromTelemetryProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeTelemetryRecordedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.recoveryDetectionPending == true) {
            commandGateway.send(MarkRuntimeAgentRecoveredCommand(nodeId = event.nodeId, runtimeAgentId = event.runtimeAgentId, federationId = event.federationId, federationName = event.federationName, trainingJobId = event.trainingJobId, trainingJobObjective = event.trainingJobObjective, roundExecutionId = event.roundExecutionId, runtimeNodeName = event.runtimeNodeName, recoveryReason = event.recoveryReason)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
