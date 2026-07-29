package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeAgentBootstrapConfigurationLoadedEvent(
    @EventTag(key = "runtimeAgentId")
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID,
    val agentVersion: String,
    val bootstrapConfigurationLoaded: Boolean
)
