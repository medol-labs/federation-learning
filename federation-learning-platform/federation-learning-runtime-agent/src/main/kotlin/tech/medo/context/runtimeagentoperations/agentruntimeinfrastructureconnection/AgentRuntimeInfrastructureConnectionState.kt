package tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnection

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionReportFailedEvent
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionEstablishedEvent
import tech.medo.runtimeagentoperations.domain.states.AgentRuntimeInfrastructureConnectionStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = AgentRuntimeInfrastructureConnectionTags.RUNTIME_INFRASTRUCTURE_ID)
class AgentRuntimeInfrastructureConnectionState @EntityCreator constructor() {

    var currentState: AgentRuntimeInfrastructureConnectionStateEnum? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var runtimePlatformConnectionReady: Boolean? = null
    var platformApiReachable: Boolean? = null
    var agentAuthenticationSucceeded: Boolean? = null
    var controlChannelEstablished: Boolean? = null
    var heartbeatAccepted: Boolean? = null
    var failureReason: String? = null
    var retryable: Boolean? = null
    var runtimeAgentEndpoint: String? = null
    var endpointScope: String? = null

    @EventSourcingHandler
    fun evolve(event: AgentRuntimeConnectionReportFailedEvent): AgentRuntimeInfrastructureConnectionState = apply {
        currentState = AgentRuntimeInfrastructureConnectionStateEnum.CONNECTED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        runtimePlatformConnectionReady = event.runtimePlatformConnectionReady
        platformApiReachable = event.platformApiReachable
        agentAuthenticationSucceeded = event.agentAuthenticationSucceeded
        controlChannelEstablished = event.controlChannelEstablished
        heartbeatAccepted = event.heartbeatAccepted
        failureReason = event.failureReason
        retryable = event.retryable
    }

    @EventSourcingHandler
    fun evolve(event: AgentRuntimeConnectionEstablishedEvent): AgentRuntimeInfrastructureConnectionState = apply {
        currentState = AgentRuntimeInfrastructureConnectionStateEnum.CONNECTED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        runtimeAgentEndpoint = event.runtimeAgentEndpoint
        endpointScope = event.endpointScope
        runtimePlatformConnectionReady = event.runtimePlatformConnectionReady
        platformApiReachable = event.platformApiReachable
        agentAuthenticationSucceeded = event.agentAuthenticationSucceeded
        controlChannelEstablished = event.controlChannelEstablished
        heartbeatAccepted = event.heartbeatAccepted
    }
}
