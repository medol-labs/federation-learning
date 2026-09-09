package tech.medo.organizationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


@Event
data class UserOrganizationMembershipUserAccountIdOrganizationIdReservedEvent(
    val userOrganizationMembershipId: UUID,
    val userAccountId: UUID,
    val organizationId: UUID,
    @EventTag(key = "userAccountId")
    val normalizedUserAccountId: String,
    @EventTag(key = "organizationId")
    val normalizedOrganizationId: String
)
