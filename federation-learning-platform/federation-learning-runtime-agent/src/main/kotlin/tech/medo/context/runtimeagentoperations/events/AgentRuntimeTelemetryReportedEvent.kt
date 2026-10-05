package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



@Event
data class AgentRuntimeTelemetryReportedEvent(
    @EventTag(key = "nodeId")
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val federationId: UUID?,
    val trainingJobId: UUID?,
    val roundExecutionId: UUID?,
    val cpuLoad: BigDecimal?,
    val gpuLoad: BigDecimal?,
    val memoryLoad: BigDecimal?,
    val lastHeartbeatAt: LocalDateTime?,
    val telemetryRetentionPolicy: String
)
