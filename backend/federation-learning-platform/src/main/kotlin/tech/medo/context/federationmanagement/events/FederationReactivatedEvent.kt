package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FederationReactivatedEvent(
    val federationId: UUID,
    val reactivationReason: String,
    @EventTag(key = "federationName")
    val federationName: String
)
