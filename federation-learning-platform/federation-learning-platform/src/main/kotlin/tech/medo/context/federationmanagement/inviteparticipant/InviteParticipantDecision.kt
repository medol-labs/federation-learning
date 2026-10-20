package tech.medo.federationmanagement.inviteparticipant

import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand


import tech.medo.federationmanagement.events.ParticipantInvitedEvent
import tech.medo.federationmanagement.events.FederationMembershipFederationIdOrganizationIdReservedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState

import tech.medo.federationmanagement.federationmembership.FederationMembershipFederationIdOrganizationIdReservationState



interface InviteParticipantDecision {
    fun decide(command: InviteParticipantCommand, federationMembershipFederationIdOrganizationIdReservation: FederationMembershipFederationIdOrganizationIdReservationState): List<Any> {
        require(!federationMembershipFederationIdOrganizationIdReservation.reserved) {
            "FederationId OrganizationId already exists."
        }
        return listOf(
                        FederationMembershipFederationIdOrganizationIdReservedEvent(federationId = command.federationId, organizationId = command.organizationId, normalizedFederationId = command.federationId.toString().trim().lowercase(), normalizedOrganizationId = command.organizationId.toString().trim().lowercase()),
            ParticipantInvitedEvent(federationId = command.federationId, federationName = command.federationName, organizationId = command.organizationId, organizationName = command.organizationName, invitationNote = command.invitationNote)
        )
    }
}
