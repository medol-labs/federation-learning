package tech.medo.organizationmanagement.userorganizationmembership

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.organizationmanagement.events.UserOrganizationMembershipUserAccountIdOrganizationIdReservedEvent
import java.util.UUID;


@EventSourced(idType = UserOrganizationMembershipUserAccountIdOrganizationIdSelection::class)
class UserOrganizationMembershipUserAccountIdOrganizationIdReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var userOrganizationMembershipId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: UserOrganizationMembershipUserAccountIdOrganizationIdSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(UserOrganizationMembershipUserAccountIdOrganizationIdReservationTags.USER_ACCOUNT_ID, selection.normalizedUserAccountId),
                Tag.of(UserOrganizationMembershipUserAccountIdOrganizationIdReservationTags.ORGANIZATION_ID, selection.normalizedOrganizationId)
        )
    }

    @EventSourcingHandler
    fun evolve(event: UserOrganizationMembershipUserAccountIdOrganizationIdReservedEvent): UserOrganizationMembershipUserAccountIdOrganizationIdReservationState = apply {
        reserved = true
        userOrganizationMembershipId = event.userOrganizationMembershipId
    }
}
