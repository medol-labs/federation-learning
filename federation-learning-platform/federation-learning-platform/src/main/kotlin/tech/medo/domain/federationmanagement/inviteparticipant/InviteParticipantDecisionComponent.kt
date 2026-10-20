package tech.medo.domain.federationmanagement.inviteparticipant

import org.springframework.stereotype.Component
import tech.medo.federationmanagement.inviteparticipant.InviteParticipantDecision
import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand
import tech.medo.federationmanagement.federationmembership.FederationMembershipFederationIdOrganizationIdReservationState
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelKey
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelRepository
import tech.medo.shared.domain.CommandRejectedException

@Component
class InviteParticipantDecisionComponent(
    private val memberships: FederationMembershipDirectoryReadModelRepository,
) : InviteParticipantDecision {
    override fun decide(
        command: InviteParticipantCommand,
        federationMembershipFederationIdOrganizationIdReservation: FederationMembershipFederationIdOrganizationIdReservationState,
    ): List<Any> {
        // Older invitations predate uniqueness reservations, so also check their exact membership key.
        if (federationMembershipFederationIdOrganizationIdReservation.reserved || memberships.findProjectionById(
                FederationMembershipDirectoryReadModelKey(command.federationId, command.organizationId)
            ) != null) {
            throw CommandRejectedException(
                code = "PARTICIPANT_ALREADY_INVITED",
                i18nKey = "errors.federationmanagement.inviteParticipant.uniqueViolation",
                args = mapOf("federationId" to command.federationId, "organizationId" to command.organizationId),
                message = "This organization already has a membership in this federation and cannot be invited again.",
            )
        }
        return super.decide(command, federationMembershipFederationIdOrganizationIdReservation)
    }
}
