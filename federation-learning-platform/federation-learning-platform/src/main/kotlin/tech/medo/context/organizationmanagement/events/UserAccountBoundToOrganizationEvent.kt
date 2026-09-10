package tech.medo.organizationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class UserAccountBoundToOrganizationEvent(
    @EventTag(key = "userOrganizationMembershipId")
    val userOrganizationMembershipId: UUID,
    val userAccountId: UUID,
    val username: String?,
    val organizationId: UUID,
    val organizationName: String?,
    val organizationUserRole: String?
)
