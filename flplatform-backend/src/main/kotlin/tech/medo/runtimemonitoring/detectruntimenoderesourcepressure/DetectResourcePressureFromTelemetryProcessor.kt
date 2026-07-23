package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModel
import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModelRepository
import tech.medo.runtimemonitoring.detectruntimenoderesourcepressure.DetectRuntimeNodeResourcePressureCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DetectResourcePressureFromTelemetryProcessor(
    private val repository: RuntimeTelemetryLatestReadModelRepository,
    private val commandGateway: CommandGateway
) {
    @Scheduled(fixedDelayString = "\${automation.detect-resource-pressure-from-telemetry.fixed-delay-ms:5000}")
    fun processTodo() {
        repository.findAll(PageRequest.of(0, 100))
            .content
            .asSequence()
            .filter { todo -> todo.resourcePressureDetected == true && todo.nodeId != null && todo.runtimeAgentId != null }
            .forEach { todo ->
                commandGateway.send(DetectRuntimeNodeResourcePressureCommand(nodeId = todo.nodeId!!, runtimeAgentId = todo.runtimeAgentId!!, trainingJobId = todo.trainingJobId, pressureType = "" /* TODO: provide pressureType */, observedValue = java.math.BigDecimal.ZERO /* TODO: provide observedValue */, thresholdValue = java.math.BigDecimal.ZERO /* TODO: provide thresholdValue */))
            }
    }
}
