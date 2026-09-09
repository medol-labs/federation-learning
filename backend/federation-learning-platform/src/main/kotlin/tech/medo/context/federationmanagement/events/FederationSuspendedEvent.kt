package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FederationSuspendedEvent(
    val federationId: UUID,
    val suspensionReason: String,
    @EventTag(key = "federationName")
    val federationName: String
)
