package tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry.ReportAgentRuntimeNodeResourceTelemetryCommand
import tech.medo.runtimeagentoperations.events.AgentRuntimeNodeResourceTelemetryReportedEvent
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class ReportAgentRuntimeNodeResourceTelemetryDecisionTest {
    @Test
    fun ReportAgentRuntimeNodeResourceTelemetryEmitsAgentRuntimeNodeResourceTelemetryReportedEvent() {
        val events = (object : ReportAgentRuntimeNodeResourceTelemetryDecision {}).decide(
            ReportAgentRuntimeNodeResourceTelemetryCommand(
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
            telemetryRetentionPolicy = ""
            )
        )

        assertTrue(events.any { it is AgentRuntimeNodeResourceTelemetryReportedEvent })
    }
}
