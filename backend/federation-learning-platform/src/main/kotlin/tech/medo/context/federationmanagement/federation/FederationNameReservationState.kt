package tech.medo.federationmanagement.federation

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.federationmanagement.events.FederationNameReservedEvent
import java.util.UUID;


@EventSourced(idType = FederationNameSelection::class)
class FederationNameReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var federationId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: FederationNameSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(FederationNameReservationTags.FEDERATION_NAME, selection.normalizedName)
        )
    }

    @EventSourcingHandler
    fun evolve(event: FederationNameReservedEvent): FederationNameReservationState = apply {
        reserved = true
        federationId = event.federationId
    }
}
