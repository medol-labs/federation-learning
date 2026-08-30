package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class UserAccountRegisteredEvent(
    @EventTag(key = "userAccountId")
    val userAccountId: UUID,
    val username: String,
    val providerSubject: String?,
    val passwordHash: String?
)
