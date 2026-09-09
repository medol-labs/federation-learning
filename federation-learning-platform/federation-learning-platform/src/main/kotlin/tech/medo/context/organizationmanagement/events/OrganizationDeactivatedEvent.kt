package tech.medo.organizationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


/* TODO: provide values for selection tags: organizationName = normalize(organizationName) */

@Event
data class OrganizationDeactivatedEvent(
    val organizationId: UUID,
    val deactivationReason: String
)
