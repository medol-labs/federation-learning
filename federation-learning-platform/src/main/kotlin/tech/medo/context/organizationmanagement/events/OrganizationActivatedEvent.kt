package tech.medo.organizationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class OrganizationActivatedEvent(
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    val activationNote: String?
)
