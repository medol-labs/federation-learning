package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event



@Event
data class RoleRegisteredEvent(
    @EventTag(key = "roleCode")
    val roleCode: String,
    val roleName: String
)
