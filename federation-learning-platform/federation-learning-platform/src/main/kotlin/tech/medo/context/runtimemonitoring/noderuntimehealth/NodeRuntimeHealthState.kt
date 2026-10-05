package tech.medo.runtimemonitoring.noderuntimehealth

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimemonitoring.events.RuntimeTelemetryRecordedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimemonitoring.domain.states.NodeRuntimeHealthStateEnum

import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@EventSourced(idType = UUID::class, tagKey = NodeRuntimeHealthTags.NODE_ID)
class NodeRuntimeHealthState @EntityCreator constructor() {

    var currentState: NodeRuntimeHealthStateEnum? = null
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var federationId: UUID? = null
    var federationName: String? = null
    var trainingJobId: UUID? = null
    var trainingJobObjective: String? = null
    var roundExecutionId: UUID? = null
    var runtimeNodeName: String? = null
    var cpuLoad: BigDecimal? = null
    var gpuLoad: BigDecimal? = null
    var memoryLoad: BigDecimal? = null
    var lastHeartbeatAt: LocalDateTime? = null
    var lastRecoveredAt: LocalDateTime? = null
    var offlineDetectionPending: Boolean? = null
    var recoveryDetectionPending: Boolean? = null
    var resourcePressureDetectionPending: Boolean? = null
    var offlineReason: String? = null
    var recoveryReason: String? = null
    var pressureType: String? = null
    var observedValue: BigDecimal? = null
    var thresholdValue: BigDecimal? = null
    var alertSeverity: String? = null
    var alertMessage: String? = null
    var healthStatus: String? = null
    var telemetryRetentionPolicy: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeTelemetryRecordedEvent): NodeRuntimeHealthState = apply {
        currentState = NodeRuntimeHealthStateEnum.Recorded
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        federationId = event.federationId
        federationName = event.federationName
        trainingJobId = event.trainingJobId
        trainingJobObjective = event.trainingJobObjective
        roundExecutionId = event.roundExecutionId
        runtimeNodeName = event.runtimeNodeName
        cpuLoad = event.cpuLoad
        gpuLoad = event.gpuLoad
        memoryLoad = event.memoryLoad
        lastHeartbeatAt = event.lastHeartbeatAt
        lastRecoveredAt = event.lastRecoveredAt
        offlineDetectionPending = event.offlineDetectionPending
        recoveryDetectionPending = event.recoveryDetectionPending
        resourcePressureDetectionPending = event.resourcePressureDetectionPending
        offlineReason = event.offlineReason
        recoveryReason = event.recoveryReason
        pressureType = event.pressureType
        observedValue = event.observedValue
        thresholdValue = event.thresholdValue
        alertSeverity = event.alertSeverity
        alertMessage = event.alertMessage
        healthStatus = event.healthStatus
        telemetryRetentionPolicy = event.telemetryRetentionPolicy
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentOfflineDetectedEvent): NodeRuntimeHealthState = apply {
        currentState = NodeRuntimeHealthStateEnum.Offline
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        federationId = event.federationId
        federationName = event.federationName
        trainingJobId = event.trainingJobId
        trainingJobObjective = event.trainingJobObjective
        roundExecutionId = event.roundExecutionId
        runtimeNodeName = event.runtimeNodeName
        offlineReason = event.offlineReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentRecoveredEvent): NodeRuntimeHealthState = apply {
        currentState = NodeRuntimeHealthStateEnum.Healthy
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        federationId = event.federationId
        federationName = event.federationName
        trainingJobId = event.trainingJobId
        trainingJobObjective = event.trainingJobObjective
        roundExecutionId = event.roundExecutionId
        runtimeNodeName = event.runtimeNodeName
        recoveryReason = event.recoveryReason
    }
}
