package tech.medo.runtimemonitoring.detectruntimenodecapacitychange

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.runtimenodecapacity.RuntimeNodeCapacitySelection
import java.util.UUID;


@Command
data class DetectRuntimeNodeCapacityChangeCommand(
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val previousCapacityHash: String?,
    val currentCapacityHash: String,
    val allocatableCpuCores: Int,
    val allocatableMemoryGb: Int,
    val allocatableGpuCount: Int
) {
    @TargetEntityId
    val selection: RuntimeNodeCapacitySelection = RuntimeNodeCapacitySelection(nodeId = nodeId)

}
