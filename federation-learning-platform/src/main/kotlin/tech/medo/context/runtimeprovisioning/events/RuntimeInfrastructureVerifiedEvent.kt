package tech.medo.runtimeprovisioning.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeInfrastructureVerifiedEvent(
    @EventTag(key = "runtimeInfrastructureId")
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val agentInstallMode: String,
    val observedNodeCount: Int
)
