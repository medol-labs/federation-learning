package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FederationActivatedEvent(
    val federationId: UUID,
    val activationNote: String?,
    @EventTag(key = "federationName")
    val federationName: String
)
