package tech.medo.runtimemonitoring.runtimenoderesourcetelemetry

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimemonitoring.events.RuntimeNodeResourceTelemetryRecordedEvent
import tech.medo.runtimemonitoring.domain.states.RuntimeNodeResourceTelemetryStateEnum

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@EventSourced(idType = UUID::class, tagKey = RuntimeNodeResourceTelemetryTags.NODE_ID)
class RuntimeNodeResourceTelemetryState @EntityCreator constructor() {

    var currentState: RuntimeNodeResourceTelemetryStateEnum? = null
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeNodeName: String? = null
    var nodeReady: Boolean? = null
    var allocatableCpuCores: Int? = null
    var allocatableMemoryGb: Int? = null
    var allocatableGpuCount: Int? = null
    var allocatedCpuCores: Int? = null
    var allocatedMemoryGb: Int? = null
    var allocatedGpuCount: Int? = null
    var availableCpuCores: Int? = null
    var availableMemoryGb: Int? = null
    var availableGpuCount: Int? = null
    var runningWorkloadCount: Int? = null
    var workloadCapacity: Int? = null
    var observedAt: LocalDateTime? = null
    var lastResourceSnapshotAt: LocalDateTime? = null
    var telemetryRetentionPolicy: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeNodeResourceTelemetryRecordedEvent): RuntimeNodeResourceTelemetryState = apply {
        currentState = RuntimeNodeResourceTelemetryStateEnum.Recorded
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeNodeName = event.runtimeNodeName
        nodeReady = event.nodeReady
        allocatableCpuCores = event.allocatableCpuCores
        allocatableMemoryGb = event.allocatableMemoryGb
        allocatableGpuCount = event.allocatableGpuCount
        allocatedCpuCores = event.allocatedCpuCores
        allocatedMemoryGb = event.allocatedMemoryGb
        allocatedGpuCount = event.allocatedGpuCount
        availableCpuCores = event.availableCpuCores
        availableMemoryGb = event.availableMemoryGb
        availableGpuCount = event.availableGpuCount
        runningWorkloadCount = event.runningWorkloadCount
        workloadCapacity = event.workloadCapacity
        observedAt = event.observedAt
        lastResourceSnapshotAt = event.lastResourceSnapshotAt
        telemetryRetentionPolicy = event.telemetryRetentionPolicy
    }
}
