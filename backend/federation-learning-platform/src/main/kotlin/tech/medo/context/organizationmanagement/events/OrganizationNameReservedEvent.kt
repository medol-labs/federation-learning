package tech.medo.organizationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


@Event
data class OrganizationNameReservedEvent(
    val organizationId: UUID,
    val organizationName: String,
    @EventTag(key = "organizationName")
    val normalizedName: String
)
