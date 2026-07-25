package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FederationCreatedEvent(
    @EventTag(key = "federationId")
    val federationId: UUID,
    @EventTag(key = "federationName")
    val federationName: String,
    val description: String,
    val minimumParticipantCount: Int
)
