package tech.medo.organizationmanagement.userorganizationmembership

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.organizationmanagement.events.UserAccountBoundToOrganizationEvent
import tech.medo.organizationmanagement.domain.states.UserOrganizationMembershipStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = UserOrganizationMembershipTags.USER_ORGANIZATION_MEMBERSHIP_ID)
class UserOrganizationMembershipState @EntityCreator constructor() {

    var currentState: UserOrganizationMembershipStateEnum? = null
    var userOrganizationMembershipId: UUID? = null
    var userAccountId: UUID? = null
    var organizationId: UUID? = null
    var organizationUserRole: String? = null

    @EventSourcingHandler
    fun evolve(event: UserAccountBoundToOrganizationEvent): UserOrganizationMembershipState = apply {
        currentState = UserOrganizationMembershipStateEnum.ACTIVE
        userOrganizationMembershipId = event.userOrganizationMembershipId
        userAccountId = event.userAccountId
        organizationId = event.organizationId
        organizationUserRole = event.organizationUserRole
    }
}
