package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FederationSuspendedEvent(
    @EventTag(key = "federationId")
    val federationId: UUID,
    val suspensionReason: String
)
