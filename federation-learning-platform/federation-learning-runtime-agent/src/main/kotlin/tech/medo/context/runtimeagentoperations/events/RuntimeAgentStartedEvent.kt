package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeAgentStartedEvent(
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID,
    val agentVersion: String,
    val runtimeAgentEndpoint: String,
    val endpointScope: String,
    @EventTag(key = "bootstrapRequestId")
    val bootstrapRequestId: UUID
)
