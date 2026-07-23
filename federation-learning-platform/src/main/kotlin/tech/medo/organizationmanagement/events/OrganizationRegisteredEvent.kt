package tech.medo.organizationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.organizationmanagement.domain.types.OrganizationType;



@Event
data class OrganizationRegisteredEvent(
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    val organizationName: String,
    val organizationType: OrganizationType,
    val contactEmail: String,
    @EventTag(key = "organizationName")
    val organizationNameEventTag: String = organizationName.trim().lowercase()
)
