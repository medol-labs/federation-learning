package tech.medo.runtimemonitoring.recordruntimenoderesourcetelemetry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimemonitoring.recordruntimenoderesourcetelemetry.RecordRuntimeNodeResourceTelemetryCommand
import tech.medo.runtimemonitoring.events.RuntimeNodeResourceTelemetryRecordedEvent
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class RecordRuntimeNodeResourceTelemetryDecisionTest {
    @Test
    fun RecordRuntimeNodeResourceTelemetryEmitsRuntimeNodeResourceTelemetryRecordedEvent() {
        val events = (object : RecordRuntimeNodeResourceTelemetryDecision {}).decide(
            RecordRuntimeNodeResourceTelemetryCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = null,
            runtimeNodeName = null,
            nodeReady = false,
            allocatableCpuCores = 0,
            allocatableMemoryGb = 0,
            allocatableGpuCount = 0,
            allocatedCpuCores = 0,
            allocatedMemoryGb = 0,
            allocatedGpuCount = 0,
            availableCpuCores = 0,
            availableMemoryGb = 0,
            availableGpuCount = 0,
            runningWorkloadCount = 0,
            workloadCapacity = 0,
            observedAt = java.time.LocalDateTime.now(),
            lastResourceSnapshotAt = java.time.LocalDateTime.now(),
            telemetryRetentionPolicy = ""
            )
        )

        assertTrue(events.any { it is RuntimeNodeResourceTelemetryRecordedEvent })
    }
}
