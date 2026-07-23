package tech.medo.organizationmanagement.organization

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.organizationmanagement.events.OrganizationActivatedEvent
import tech.medo.organizationmanagement.events.OrganizationDeactivatedEvent
import tech.medo.organizationmanagement.events.OrganizationReactivatedEvent
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum

import java.util.UUID;
import tech.medo.organizationmanagement.domain.types.OrganizationType;


@EventSourced(idType = UUID::class, tagKey = OrganizationTags.ORGANIZATION_ID)
class OrganizationState @EntityCreator constructor() {

    var currentState: OrganizationStateEnum? = null
    private var organizationId: UUID? = null
    private var organizationName: String? = null
    private var organizationType: OrganizationType? = null
    private var contactEmail: String? = null
    private var activationNote: String? = null
    private var deactivationReason: String? = null
    private var reactivationReason: String? = null

    @EventSourcingHandler
    fun evolve(event: OrganizationRegisteredEvent): OrganizationState = apply {
        currentState = OrganizationStateEnum.REGISTERED
        organizationId = event.organizationId
        organizationName = event.organizationName
        organizationType = event.organizationType
        contactEmail = event.contactEmail
    }

    @EventSourcingHandler
    fun evolve(event: OrganizationActivatedEvent): OrganizationState = apply {
        currentState = OrganizationStateEnum.ACTIVE
        organizationId = event.organizationId
        activationNote = event.activationNote
    }

    @EventSourcingHandler
    fun evolve(event: OrganizationDeactivatedEvent): OrganizationState = apply {
        currentState = OrganizationStateEnum.DEACTIVATED
        organizationId = event.organizationId
        deactivationReason = event.deactivationReason
    }

    @EventSourcingHandler
    fun evolve(event: OrganizationReactivatedEvent): OrganizationState = apply {
        currentState = OrganizationStateEnum.ACTIVE
        organizationId = event.organizationId
        reactivationReason = event.reactivationReason
    }
}
