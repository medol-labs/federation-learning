package tech.medo.runtimemonitoring.detectruntimeagentoffline

import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModel
import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModelRepository
import tech.medo.runtimemonitoring.detectruntimeagentoffline.DetectRuntimeAgentOfflineCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DetectOfflineRuntimeAgentFromTelemetryProcessor(
    private val repository: RuntimeTelemetryLatestReadModelRepository,
    private val commandGateway: CommandGateway
) {
    @Scheduled(fixedDelayString = "\${automation.detect-offline-runtime-agent-from-telemetry.fixed-delay-ms:5000}")
    fun processTodo() {
        repository.findAll(PageRequest.of(0, 100))
            .content
            .asSequence()
            .filter { todo -> todo.heartbeatMissingBeyondThreshold == true && todo.nodeId != null && todo.runtimeAgentId != null }
            .forEach { todo ->
                commandGateway.send(DetectRuntimeAgentOfflineCommand(nodeId = todo.nodeId!!, runtimeAgentId = todo.runtimeAgentId!!, federationId = todo.federationId, federationName = todo.federationName, trainingJobId = todo.trainingJobId, trainingJobObjective = todo.trainingJobObjective, roundExecutionId = todo.roundExecutionId, runtimeNodeName = todo.runtimeNodeName, offlineReason = "" /* TODO: provide offlineReason */))
            }
    }
}
