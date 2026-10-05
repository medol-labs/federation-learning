package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



@Event
data class RuntimeTelemetryRecordedEvent(
    @EventTag(key = "nodeId")
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val federationId: UUID?,
    val federationName: String?,
    val trainingJobId: UUID?,
    val trainingJobObjective: String?,
    val roundExecutionId: UUID?,
    val runtimeNodeName: String?,
    val cpuLoad: BigDecimal?,
    val gpuLoad: BigDecimal?,
    val memoryLoad: BigDecimal?,
    val lastHeartbeatAt: LocalDateTime?,
    val lastRecoveredAt: LocalDateTime?,
    val offlineDetectionPending: Boolean,
    val recoveryDetectionPending: Boolean,
    val resourcePressureDetectionPending: Boolean,
    val offlineReason: String?,
    val recoveryReason: String?,
    val pressureType: String?,
    val observedValue: BigDecimal?,
    val thresholdValue: BigDecimal?,
    val alertSeverity: String?,
    val alertMessage: String?,
    val healthStatus: String,
    val telemetryRetentionPolicy: String
)
