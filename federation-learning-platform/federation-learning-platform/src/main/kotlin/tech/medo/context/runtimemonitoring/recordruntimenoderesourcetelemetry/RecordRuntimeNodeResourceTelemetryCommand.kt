package tech.medo.runtimemonitoring.recordruntimenoderesourcetelemetry

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.runtimenoderesourcetelemetry.RuntimeNodeResourceTelemetrySelection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class RecordRuntimeNodeResourceTelemetryCommand(
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID?,
    val runtimeNodeName: String?,
    val nodeReady: Boolean,
    val allocatableCpuCores: Int,
    val allocatableMemoryGb: Int,
    val allocatableGpuCount: Int,
    val allocatedCpuCores: Int,
    val allocatedMemoryGb: Int,
    val allocatedGpuCount: Int,
    val availableCpuCores: Int,
    val availableMemoryGb: Int,
    val availableGpuCount: Int,
    val runningWorkloadCount: Int,
    val workloadCapacity: Int,
    val observedAt: LocalDateTime,
    val lastResourceSnapshotAt: LocalDateTime,
    val telemetryRetentionPolicy: String
) {
    @TargetEntityId
    val selection: RuntimeNodeResourceTelemetrySelection = RuntimeNodeResourceTelemetrySelection(nodeId = nodeId)


}
