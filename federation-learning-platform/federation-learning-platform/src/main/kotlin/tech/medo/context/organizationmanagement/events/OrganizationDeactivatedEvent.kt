package tech.medo.organizationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class OrganizationDeactivatedEvent(
    val organizationId: UUID,
    val organizationName: String,
    val deactivationReason: String,
    @EventTag(key = "organizationName")
    val organizationNameEventTag: String = organizationName.trim().lowercase()
)
