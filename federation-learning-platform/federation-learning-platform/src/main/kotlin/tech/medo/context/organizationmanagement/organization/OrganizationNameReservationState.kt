package tech.medo.organizationmanagement.organization

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.organizationmanagement.events.OrganizationNameReservedEvent
import java.util.UUID;


@EventSourced(idType = OrganizationNameSelection::class)
class OrganizationNameReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var organizationId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: OrganizationNameSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(OrganizationNameReservationTags.ORGANIZATION_NAME, selection.normalizedName)
        )
    }

    @EventSourcingHandler
    fun evolve(event: OrganizationNameReservedEvent): OrganizationNameReservationState = apply {
        reserved = true
        organizationId = event.organizationId
    }
}
