package tech.medo.federationmanagement.federationmembership

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.federationmanagement.events.ParticipantInvitedEvent
import tech.medo.federationmanagement.events.ParticipantJoinedEvent
import tech.medo.federationmanagement.events.ParticipantRejectedEvent
import tech.medo.federationmanagement.events.ParticipantInvitationRevokedEvent
import tech.medo.federationmanagement.events.ParticipantSuspendedEvent
import tech.medo.federationmanagement.events.ParticipantRemovedEvent
import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum

import java.util.UUID;


@EventSourced(idType = FederationMembershipSelection::class)
class FederationMembershipState @EntityCreator constructor() {
    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: FederationMembershipSelection): EventCriteria = EventCriteria.either(
                EventCriteria.havingTags(Tag.of(FederationMembershipTags.FEDERATION_ID, selection.federationId.toString())),
                EventCriteria.havingTags(Tag.of(FederationMembershipTags.ORGANIZATION_ID, selection.organizationId.toString()))
        )
    }


    var currentState: FederationMembershipStateEnum? = null
    var federationId: UUID? = null
    var federationName: String? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var invitationNote: String? = null
    var approvalNote: String? = null
    var rejectionReason: String? = null
    var revokeReason: String? = null
    var suspensionReason: String? = null
    var removalReason: String? = null

    @EventSourcingHandler
    fun evolve(event: ParticipantInvitedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.INVITED
        federationId = event.federationId
        federationName = event.federationName
        organizationId = event.organizationId
        organizationName = event.organizationName
        invitationNote = event.invitationNote
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantJoinedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.ACTIVE
        federationId = event.federationId
        federationName = event.federationName
        organizationId = event.organizationId
        organizationName = event.organizationName
        approvalNote = event.approvalNote
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantRejectedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.REJECTED
        federationId = event.federationId
        federationName = event.federationName
        organizationId = event.organizationId
        organizationName = event.organizationName
        rejectionReason = event.rejectionReason
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantInvitationRevokedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.INVITATION_REVOKED
        federationId = event.federationId
        federationName = event.federationName
        organizationId = event.organizationId
        organizationName = event.organizationName
        revokeReason = event.revokeReason
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantSuspendedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.SUSPENDED
        federationId = event.federationId
        federationName = event.federationName
        organizationId = event.organizationId
        organizationName = event.organizationName
        suspensionReason = event.suspensionReason
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantRemovedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.REMOVED
        federationId = event.federationId
        federationName = event.federationName
        organizationId = event.organizationId
        organizationName = event.organizationName
        removalReason = event.removalReason
    }
}
