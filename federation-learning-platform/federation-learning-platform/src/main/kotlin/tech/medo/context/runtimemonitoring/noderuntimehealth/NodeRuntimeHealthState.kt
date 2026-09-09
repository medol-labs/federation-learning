package tech.medo.runtimemonitoring.noderuntimehealth

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimemonitoring.domain.states.NodeRuntimeHealthStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = NodeRuntimeHealthTags.NODE_ID)
class NodeRuntimeHealthState @EntityCreator constructor() {

    var currentState: NodeRuntimeHealthStateEnum? = null
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var offlineReason: String? = null
    var recoveryReason: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentOfflineDetectedEvent): NodeRuntimeHealthState = apply {
        currentState = NodeRuntimeHealthStateEnum.OFFLINE
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        offlineReason = event.offlineReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentRecoveredEvent): NodeRuntimeHealthState = apply {
        currentState = NodeRuntimeHealthStateEnum.HEALTHY
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        recoveryReason = event.recoveryReason
    }
}
