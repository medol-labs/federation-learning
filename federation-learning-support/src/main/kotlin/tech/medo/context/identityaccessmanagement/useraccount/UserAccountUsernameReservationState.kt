package tech.medo.identityaccessmanagement.useraccount

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.identityaccessmanagement.events.UserAccountUsernameReservedEvent
import java.util.UUID;


@EventSourced(idType = UserAccountUsernameSelection::class)
class UserAccountUsernameReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var userAccountId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: UserAccountUsernameSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(UserAccountUsernameReservationTags.USERNAME, selection.normalizedName)
        )
    }

    @EventSourcingHandler
    fun evolve(event: UserAccountUsernameReservedEvent): UserAccountUsernameReservationState = apply {
        reserved = true
        userAccountId = event.userAccountId
    }
}
