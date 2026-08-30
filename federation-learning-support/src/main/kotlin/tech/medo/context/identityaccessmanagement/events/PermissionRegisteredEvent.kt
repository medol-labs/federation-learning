package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event



@Event
data class PermissionRegisteredEvent(
    @EventTag(key = "permissionCode")
    val permissionCode: String,
    val permissionName: String,
    val description: String?
)
