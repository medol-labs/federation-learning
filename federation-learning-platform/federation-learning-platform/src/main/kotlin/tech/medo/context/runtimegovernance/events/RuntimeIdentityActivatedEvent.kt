package tech.medo.runtimegovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeIdentityActivatedEvent(
    @EventTag(key = "runtimeId")
    val runtimeId: UUID,
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val runtimeName: String
)
