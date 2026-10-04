package tech.medo.federationmanagement.inviteparticipant

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand

import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModelRepository
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelRepository
import tech.medo.federationmanagement.domain.states.FederationStateEnum
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum




@Component
class InviteParticipantCommandHandler(
    private val decision: InviteParticipantDecision,
    private val federationOverviewReadModelRepository: FederationOverviewReadModelRepository,
    private val organizationDirectoryReadModelRepository: OrganizationDirectoryReadModelRepository
) {
    @CommandHandler
    fun handle(
        command: InviteParticipantCommand,
        eventAppender: EventAppender
    ) {
        val federationOverviewReadModelSelection = federationOverviewReadModelRepository.findById(command.federationId)
        require(federationOverviewReadModelSelection != null && federationOverviewReadModelSelection.state == FederationStateEnum.Active) {
            "Federation Overview selection is not eligible."
        }
        val organizationDirectoryReadModelSelection = organizationDirectoryReadModelRepository.findById(command.organizationId)
        require(organizationDirectoryReadModelSelection != null && organizationDirectoryReadModelSelection.state == OrganizationStateEnum.Active) {
            "Organization Directory selection is not eligible for Invite Participant."
        }
        eventAppender.append(decision.decide(command))
    }
}
