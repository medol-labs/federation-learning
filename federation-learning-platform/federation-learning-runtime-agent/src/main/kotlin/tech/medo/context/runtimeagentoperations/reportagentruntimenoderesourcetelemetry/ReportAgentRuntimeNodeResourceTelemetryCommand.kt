package tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.agentruntimenoderesourcetelemetry.AgentRuntimeNodeResourceTelemetrySelection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class ReportAgentRuntimeNodeResourceTelemetryCommand(
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
    val telemetryRetentionPolicy: String
) {
    @TargetEntityId
    val selection: AgentRuntimeNodeResourceTelemetrySelection = AgentRuntimeNodeResourceTelemetrySelection(nodeId = nodeId)


}
