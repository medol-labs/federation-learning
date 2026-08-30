package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


@Event
data class UserAccountUsernameReservedEvent(
    val userAccountId: UUID,
    val username: String,
    @EventTag(key = "username")
    val normalizedName: String
)
