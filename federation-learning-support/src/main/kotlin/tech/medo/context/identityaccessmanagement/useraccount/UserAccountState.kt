package tech.medo.identityaccessmanagement.useraccount

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.identityaccessmanagement.events.UserAccountRegisteredEvent
import tech.medo.identityaccessmanagement.events.UserAccountDeactivatedEvent
import tech.medo.identityaccessmanagement.events.RoleAssignedToUserEvent
import tech.medo.identityaccessmanagement.domain.states.UserAccountStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = UserAccountTags.USER_ACCOUNT_ID)
class UserAccountState @EntityCreator constructor() {

    var currentState: UserAccountStateEnum? = null
    var userAccountId: UUID? = null
    var username: String? = null
    var providerSubject: String? = null
    var organizationId: UUID? = null
    var reason: String? = null
    var roleCode: String? = null

    @EventSourcingHandler
    fun evolve(event: UserAccountRegisteredEvent): UserAccountState = apply {
        userAccountId = event.userAccountId
        username = event.username
        providerSubject = event.providerSubject
        organizationId = event.organizationId
    }

    @EventSourcingHandler
    fun evolve(event: UserAccountDeactivatedEvent): UserAccountState = apply {
        currentState = UserAccountStateEnum.DEACTIVATED
        userAccountId = event.userAccountId
        reason = event.reason
    }

    @EventSourcingHandler
    fun evolve(event: RoleAssignedToUserEvent): UserAccountState = apply {
        userAccountId = event.userAccountId
        roleCode = event.roleCode
    }
}
