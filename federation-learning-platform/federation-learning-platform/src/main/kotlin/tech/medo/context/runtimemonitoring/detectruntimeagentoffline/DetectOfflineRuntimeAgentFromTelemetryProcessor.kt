package tech.medo.runtimemonitoring.detectruntimeagentoffline

import tech.medo.runtimemonitoring.events.RuntimeTelemetryRecordedEvent
import tech.medo.runtimemonitoring.detectruntimeagentoffline.DetectRuntimeAgentOfflineCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-monitoring-detect-runtime-agent-offline")
@Component
class DetectOfflineRuntimeAgentFromTelemetryProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeTelemetryRecordedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.offlineDetectionPending == true) {
            commandGateway.send(DetectRuntimeAgentOfflineCommand(nodeId = event.nodeId, runtimeAgentId = event.runtimeAgentId, federationId = event.federationId, federationName = event.federationName, trainingJobId = event.trainingJobId, trainingJobObjective = event.trainingJobObjective, roundExecutionId = event.roundExecutionId, runtimeNodeName = event.runtimeNodeName, offlineReason = event.offlineReason!!)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
