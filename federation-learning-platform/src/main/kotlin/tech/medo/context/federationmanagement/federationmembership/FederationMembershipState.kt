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
    private var federationId: UUID? = null
    private var organizationId: UUID? = null
    private var invitationNote: String? = null
    private var approvalNote: String? = null
    private var rejectionReason: String? = null
    private var revokeReason: String? = null
    private var suspensionReason: String? = null
    private var removalReason: String? = null

    @EventSourcingHandler
    fun evolve(event: ParticipantInvitedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.INVITED
        federationId = event.federationId
        organizationId = event.organizationId
        invitationNote = event.invitationNote
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantJoinedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.ACTIVE
        federationId = event.federationId
        organizationId = event.organizationId
        approvalNote = event.approvalNote
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantRejectedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.REJECTED
        federationId = event.federationId
        organizationId = event.organizationId
        rejectionReason = event.rejectionReason
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantInvitationRevokedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.INVITATION_REVOKED
        federationId = event.federationId
        organizationId = event.organizationId
        revokeReason = event.revokeReason
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantSuspendedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.SUSPENDED
        federationId = event.federationId
        organizationId = event.organizationId
        suspensionReason = event.suspensionReason
    }

    @EventSourcingHandler
    fun evolve(event: ParticipantRemovedEvent): FederationMembershipState = apply {
        currentState = FederationMembershipStateEnum.REMOVED
        federationId = event.federationId
        organizationId = event.organizationId
        removalReason = event.removalReason
    }
}
