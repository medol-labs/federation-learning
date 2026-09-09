package tech.medo.federationmanagement.federation

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.federationmanagement.events.FederationActivatedEvent
import tech.medo.federationmanagement.events.FederationSuspendedEvent
import tech.medo.federationmanagement.events.FederationReactivatedEvent
import tech.medo.federationmanagement.domain.states.FederationStateEnum

import java.util.UUID;


@EventSourced(idType = String::class, tagKey = FederationTags.FEDERATION_NAME)
class FederationState @EntityCreator constructor() {

    var currentState: FederationStateEnum? = null
    var federationId: UUID? = null
    var federationName: String? = null
    var description: String? = null
    var minimumParticipantCount: Int? = null
    var activationNote: String? = null
    var suspensionReason: String? = null
    var reactivationReason: String? = null

    @EventSourcingHandler
    fun evolve(event: FederationCreatedEvent): FederationState = apply {
        currentState = FederationStateEnum.DRAFT
        federationId = event.federationId
        federationName = event.federationName
        description = event.description
        minimumParticipantCount = event.minimumParticipantCount
    }

    @EventSourcingHandler
    fun evolve(event: FederationActivatedEvent): FederationState = apply {
        currentState = FederationStateEnum.ACTIVE
        federationId = event.federationId
        activationNote = event.activationNote
    }

    @EventSourcingHandler
    fun evolve(event: FederationSuspendedEvent): FederationState = apply {
        currentState = FederationStateEnum.SUSPENDED
        federationId = event.federationId
        suspensionReason = event.suspensionReason
    }

    @EventSourcingHandler
    fun evolve(event: FederationReactivatedEvent): FederationState = apply {
        currentState = FederationStateEnum.ACTIVE
        federationId = event.federationId
        reactivationReason = event.reactivationReason
    }
}
