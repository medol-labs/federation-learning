package tech.medo.runtimemonitoring.runtimenodecapacity

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimemonitoring.events.RuntimeNodeCapacityChangedEvent
import tech.medo.runtimemonitoring.domain.states.RuntimeNodeCapacityStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = RuntimeNodeCapacityTags.NODE_ID)
class RuntimeNodeCapacityState @EntityCreator constructor() {

    var currentState: RuntimeNodeCapacityStateEnum? = null
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var previousCapacityHash: String? = null
    var currentCapacityHash: String? = null
    var allocatableCpuCores: Int? = null
    var allocatableMemoryGb: Int? = null
    var allocatableGpuCount: Int? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeNodeCapacityChangedEvent): RuntimeNodeCapacityState = apply {
        currentState = RuntimeNodeCapacityStateEnum.CAPACITY_CHANGED
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        previousCapacityHash = event.previousCapacityHash
        currentCapacityHash = event.currentCapacityHash
        allocatableCpuCores = event.allocatableCpuCores
        allocatableMemoryGb = event.allocatableMemoryGb
        allocatableGpuCount = event.allocatableGpuCount
    }
}
