package tech.medo.runtimeagentoperations.reportagentruntimetelemetry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportagentruntimetelemetry.ReportAgentRuntimeTelemetryCommand
import tech.medo.runtimeagentoperations.events.AgentRuntimeTelemetryReportedEvent
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class ReportAgentRuntimeTelemetryDecisionTest {
    @Test
    fun ReportAgentRuntimeTelemetryEmitsAgentRuntimeTelemetryReportedEvent() {
        val events = (object : ReportAgentRuntimeTelemetryDecision {}).decide(
            ReportAgentRuntimeTelemetryCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            federationId = null,
            trainingJobId = null,
            roundExecutionId = null,
            cpuLoad = null,
            gpuLoad = null,
            memoryLoad = null,
            lastHeartbeatAt = null,
            telemetryRetentionPolicy = ""
            )
        )

        assertTrue(events.any { it is AgentRuntimeTelemetryReportedEvent })
    }
}
