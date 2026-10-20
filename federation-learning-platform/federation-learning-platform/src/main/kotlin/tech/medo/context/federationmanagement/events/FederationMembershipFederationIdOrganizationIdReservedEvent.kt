package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


@Event
data class FederationMembershipFederationIdOrganizationIdReservedEvent(
    val federationId: UUID,
    val organizationId: UUID,
    @EventTag(key = "federationId")
    val normalizedFederationId: String,
    @EventTag(key = "organizationId")
    val normalizedOrganizationId: String
)
