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
    var federationId: UUID? = null
    var federationName: String? = null
    var trainingJobId: UUID? = null
    var trainingJobObjective: String? = null
    var roundExecutionId: UUID? = null
    var runtimeNodeName: String? = null
    var offlineReason: String? = null
    var recoveryReason: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentOfflineDetectedEvent): NodeRuntimeHealthState = apply {
        currentState = NodeRuntimeHealthStateEnum.OFFLINE
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
        currentState = NodeRuntimeHealthStateEnum.HEALTHY
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
