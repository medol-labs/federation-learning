package tech.medo.federationmanagement.inviteparticipant

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.federationmanagement.federationmembership.FederationMembershipSelection
import java.util.UUID;

import tech.medo.federationmanagement.federationmembership.FederationMembershipFederationIdOrganizationIdSelection

@Command
data class InviteParticipantCommand(
    val federationId: UUID,
    val federationName: String?,
    val organizationId: UUID,
    val organizationName: String?,
    val invitationNote: String
) {

    @TargetEntityId
    val selection: FederationMembershipSelection = FederationMembershipSelection(federationId = federationId, organizationId = organizationId)



    val federationMembershipFederationIdOrganizationIdSelection: FederationMembershipFederationIdOrganizationIdSelection = FederationMembershipFederationIdOrganizationIdSelection(normalizedFederationId = federationId.toString().trim().lowercase(), normalizedOrganizationId = organizationId.toString().trim().lowercase())
}
