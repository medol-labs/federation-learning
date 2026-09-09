package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class UserAccountDeactivatedEvent(
    @EventTag(key = "userAccountId")
    val userAccountId: UUID,
    val reason: String
)
