package tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnection

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionEstablishedEvent
import tech.medo.runtimeagentoperations.domain.states.AgentRuntimeInfrastructureConnectionStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = AgentRuntimeInfrastructureConnectionTags.RUNTIME_INFRASTRUCTURE_ID)
class AgentRuntimeInfrastructureConnectionState @EntityCreator constructor() {

    var currentState: AgentRuntimeInfrastructureConnectionStateEnum? = null
    private var runtimeInfrastructureId: UUID? = null
    private var runtimeAgentId: UUID? = null
    private var runtimePlatformConnectionReady: Boolean? = null
    private var platformApiReachable: Boolean? = null
    private var agentAuthenticationSucceeded: Boolean? = null
    private var controlChannelEstablished: Boolean? = null
    private var heartbeatAccepted: Boolean? = null

    @EventSourcingHandler
    fun evolve(event: AgentRuntimeConnectionEstablishedEvent): AgentRuntimeInfrastructureConnectionState = apply {
        currentState = AgentRuntimeInfrastructureConnectionStateEnum.CONNECTED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        runtimePlatformConnectionReady = event.runtimePlatformConnectionReady
        platformApiReachable = event.platformApiReachable
        agentAuthenticationSucceeded = event.agentAuthenticationSucceeded
        controlChannelEstablished = event.controlChannelEstablished
        heartbeatAccepted = event.heartbeatAccepted
    }
}
