package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RoleUnassignedFromUserEvent(
    @EventTag(key = "userAccountId")
    val userAccountId: UUID,
    val roleCode: String
)
