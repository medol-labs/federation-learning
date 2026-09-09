package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


@Event
data class FederationNameReservedEvent(
    val federationId: UUID,
    val federationName: String,
    @EventTag(key = "federationName")
    val normalizedName: String
)
