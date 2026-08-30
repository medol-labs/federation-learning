package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RoleRegisteredEvent(
    val roleId: UUID,
    val roleCode: String,
    val roleName: String,
    @EventTag(key = "roleCode")
    val roleCodeEventTag: String = roleCode.trim().lowercase()
)
