package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

import tech.medo.runtimemonitoring.events.RuntimeTelemetryRecordedEvent
import tech.medo.runtimemonitoring.detectruntimenoderesourcepressure.DetectRuntimeNodeResourcePressureCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-monitoring-detect-runtime-node-resource-pressure")
@Component
class DetectResourcePressureFromTelemetryProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeTelemetryRecordedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.resourcePressureDetectionPending == true) {
            commandGateway.send(DetectRuntimeNodeResourcePressureCommand(nodeId = event.nodeId, runtimeAgentId = event.runtimeAgentId, federationId = event.federationId, federationName = event.federationName, trainingJobId = event.trainingJobId, trainingJobObjective = event.trainingJobObjective, roundExecutionId = event.roundExecutionId, runtimeNodeName = event.runtimeNodeName, pressureType = event.pressureType!!, observedValue = event.observedValue!!, thresholdValue = event.thresholdValue!!, alertSeverity = event.alertSeverity!!, alertMessage = event.alertMessage!!)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
