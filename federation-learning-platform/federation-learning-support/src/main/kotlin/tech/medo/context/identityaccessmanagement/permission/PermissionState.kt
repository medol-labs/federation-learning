package tech.medo.identityaccessmanagement.permission

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.identityaccessmanagement.events.PermissionRegisteredEvent
import tech.medo.identityaccessmanagement.domain.states.PermissionStateEnum

import java.util.UUID;


@EventSourced(idType = String::class, tagKey = PermissionTags.PERMISSION_CODE)
class PermissionState @EntityCreator constructor() {

    var currentState: PermissionStateEnum? = null
    var permissionId: UUID? = null
    var permissionCode: String? = null
    var permissionName: String? = null
    var description: String? = null

    @EventSourcingHandler
    fun evolve(event: PermissionRegisteredEvent): PermissionState = apply {
        currentState = PermissionStateEnum.REGISTERED
        permissionId = event.permissionId
        permissionCode = event.permissionCode
        permissionName = event.permissionName
        description = event.description
    }
}
