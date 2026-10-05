package tech.medo.runtimeagentoperations.agentruntimetelemetry

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.AgentRuntimeTelemetryReportedEvent
import tech.medo.runtimeagentoperations.domain.states.AgentRuntimeTelemetryStateEnum

import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@EventSourced(idType = UUID::class, tagKey = AgentRuntimeTelemetryTags.NODE_ID)
class AgentRuntimeTelemetryState @EntityCreator constructor() {

    var currentState: AgentRuntimeTelemetryStateEnum? = null
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var federationId: UUID? = null
    var trainingJobId: UUID? = null
    var roundExecutionId: UUID? = null
    var cpuLoad: BigDecimal? = null
    var gpuLoad: BigDecimal? = null
    var memoryLoad: BigDecimal? = null
    var lastHeartbeatAt: LocalDateTime? = null
    var telemetryRetentionPolicy: String? = null

    @EventSourcingHandler
    fun evolve(event: AgentRuntimeTelemetryReportedEvent): AgentRuntimeTelemetryState = apply {
        currentState = AgentRuntimeTelemetryStateEnum.Reported
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        federationId = event.federationId
        trainingJobId = event.trainingJobId
        roundExecutionId = event.roundExecutionId
        cpuLoad = event.cpuLoad
        gpuLoad = event.gpuLoad
        memoryLoad = event.memoryLoad
        lastHeartbeatAt = event.lastHeartbeatAt
        telemetryRetentionPolicy = event.telemetryRetentionPolicy
    }
}
