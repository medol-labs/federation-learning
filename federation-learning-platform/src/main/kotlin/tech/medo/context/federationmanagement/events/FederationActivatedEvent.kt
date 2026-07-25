package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FederationActivatedEvent(
    @EventTag(key = "federationId")
    val federationId: UUID,
    val activationNote: String?
)
