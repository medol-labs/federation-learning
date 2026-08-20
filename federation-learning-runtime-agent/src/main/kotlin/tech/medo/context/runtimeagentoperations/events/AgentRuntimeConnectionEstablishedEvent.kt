package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class AgentRuntimeConnectionEstablishedEvent(
    @EventTag(key = "runtimeInfrastructureId")
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val runtimeAgentEndpoint: String,
    val endpointScope: String,
    val runtimePlatformConnectionReady: Boolean,
    val platformApiReachable: Boolean,
    val agentAuthenticationSucceeded: Boolean,
    val controlChannelEstablished: Boolean,
    val heartbeatAccepted: Boolean
)
