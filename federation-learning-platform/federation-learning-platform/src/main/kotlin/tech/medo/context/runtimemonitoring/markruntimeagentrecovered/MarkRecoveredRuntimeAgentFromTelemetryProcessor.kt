package tech.medo.runtimemonitoring.markruntimeagentrecovered

import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModel
import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModelRepository
import tech.medo.runtimemonitoring.markruntimeagentrecovered.MarkRuntimeAgentRecoveredCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MarkRecoveredRuntimeAgentFromTelemetryProcessor(
    private val repository: RuntimeTelemetryLatestReadModelRepository,
    private val commandGateway: CommandGateway
) {
    @Scheduled(fixedDelayString = "\${automation.mark-recovered-runtime-agent-from-telemetry.fixed-delay-ms:5000}")
    fun processTodo() {
        repository.findAll(PageRequest.of(0, 100))
            .content
            .asSequence()
            .filter { todo -> todo.heartbeatObservedAfterOffline == true && todo.nodeId != null && todo.runtimeAgentId != null }
            .forEach { todo ->
                commandGateway.send(MarkRuntimeAgentRecoveredCommand(nodeId = todo.nodeId!!, runtimeAgentId = todo.runtimeAgentId!!, federationId = todo.federationId, federationName = todo.federationName, trainingJobId = todo.trainingJobId, trainingJobObjective = todo.trainingJobObjective, roundExecutionId = todo.roundExecutionId, runtimeNodeName = todo.runtimeNodeName, recoveryReason = null /* TODO: provide recoveryReason */))
            }
    }
}
