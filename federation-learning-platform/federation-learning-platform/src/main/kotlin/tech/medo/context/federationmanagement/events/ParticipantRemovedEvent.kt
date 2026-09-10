package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ParticipantRemovedEvent(
    @EventTag(key = "federationId")
    val federationId: UUID,
    val federationName: String?,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    val organizationName: String?,
    val removalReason: String
)
