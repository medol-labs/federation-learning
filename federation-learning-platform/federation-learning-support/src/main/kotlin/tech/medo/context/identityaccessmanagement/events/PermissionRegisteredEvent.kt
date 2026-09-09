package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class PermissionRegisteredEvent(
    val permissionId: UUID,
    val permissionCode: String,
    val permissionName: String,
    val description: String?,
    @EventTag(key = "permissionCode")
    val permissionCodeEventTag: String = permissionCode.trim().lowercase()
)
