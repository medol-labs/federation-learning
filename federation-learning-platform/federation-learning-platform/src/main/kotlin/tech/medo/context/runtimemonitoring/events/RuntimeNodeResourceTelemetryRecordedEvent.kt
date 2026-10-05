package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



@Event
data class RuntimeNodeResourceTelemetryRecordedEvent(
    @EventTag(key = "nodeId")
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
)
