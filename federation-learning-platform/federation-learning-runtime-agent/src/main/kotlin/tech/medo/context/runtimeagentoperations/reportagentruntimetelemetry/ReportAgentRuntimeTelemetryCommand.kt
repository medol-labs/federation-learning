package tech.medo.runtimeagentoperations.reportagentruntimetelemetry

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.agentruntimetelemetry.AgentRuntimeTelemetrySelection
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class ReportAgentRuntimeTelemetryCommand(
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
) {
    @TargetEntityId
    val selection: AgentRuntimeTelemetrySelection = AgentRuntimeTelemetrySelection(nodeId = nodeId)


}
