package tech.medo.runtimemonitoring.detectruntimenodecapacitychange

import tech.medo.runtimemonitoring.runtimenoderesourcelatest.RuntimeNodeResourceLatestReadModel
import tech.medo.runtimemonitoring.runtimenoderesourcelatest.RuntimeNodeResourceLatestReadModelRepository
import tech.medo.runtimemonitoring.detectruntimenodecapacitychange.DetectRuntimeNodeCapacityChangeCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DetectCapacityChangeFromResourceTelemetryProcessor(
    private val repository: RuntimeNodeResourceLatestReadModelRepository,
    private val commandGateway: CommandGateway
) {
    @Scheduled(fixedDelayString = "\${automation.detect-capacity-change-from-resource-telemetry.fixed-delay-ms:5000}")
    fun processTodo() {
        repository.findAll(PageRequest.of(0, 100))
            .content
            .asSequence()
            .filter { todo -> todo.allocatableCapacityChanged == true && todo.nodeId != null && todo.runtimeAgentId != null && todo.allocatableCpuCores != null && todo.allocatableMemoryGb != null && todo.allocatableGpuCount != null }
            .forEach { todo ->
                commandGateway.send(DetectRuntimeNodeCapacityChangeCommand(nodeId = todo.nodeId!!, runtimeAgentId = todo.runtimeAgentId!!, previousCapacityHash = null /* TODO: provide previousCapacityHash */, currentCapacityHash = "" /* TODO: provide currentCapacityHash */, allocatableCpuCores = todo.allocatableCpuCores!!, allocatableMemoryGb = todo.allocatableMemoryGb!!, allocatableGpuCount = todo.allocatableGpuCount!!))
            }
    }
}
