package tech.medo.identityaccessmanagement.role

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.identityaccessmanagement.events.RoleRegisteredEvent
import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent
import tech.medo.identityaccessmanagement.domain.states.RoleStateEnum

import java.util.UUID;


@EventSourced(idType = String::class, tagKey = RoleTags.ROLE_CODE)
class RoleState @EntityCreator constructor() {

    var currentState: RoleStateEnum? = null
    var roleId: UUID? = null
    var roleCode: String? = null
    var roleName: String? = null
    var permissionCodes: List<String> = emptyList()

    @EventSourcingHandler
    fun evolve(event: RoleRegisteredEvent): RoleState = apply {
        currentState = RoleStateEnum.REGISTERED
        roleId = event.roleId
        roleCode = event.roleCode
        roleName = event.roleName
    }

    @EventSourcingHandler
    fun evolve(event: PermissionGrantedToRoleEvent): RoleState = apply {
        roleId = event.roleId
        roleCode = event.roleCode
        permissionCodes = event.permissionCodes
    }
}
