package tech.medo.federationmanagement.federationmembership

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.federationmanagement.events.FederationMembershipFederationIdOrganizationIdReservedEvent
import java.util.UUID;


@EventSourced(idType = FederationMembershipFederationIdOrganizationIdSelection::class)
class FederationMembershipFederationIdOrganizationIdReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var federationId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: FederationMembershipFederationIdOrganizationIdSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(FederationMembershipFederationIdOrganizationIdReservationTags.FEDERATION_ID, selection.normalizedFederationId),
                Tag.of(FederationMembershipFederationIdOrganizationIdReservationTags.ORGANIZATION_ID, selection.normalizedOrganizationId)
        )
    }

    @EventSourcingHandler
    fun evolve(event: FederationMembershipFederationIdOrganizationIdReservedEvent): FederationMembershipFederationIdOrganizationIdReservationState = apply {
        reserved = true
        federationId = event.federationId
    }
}
