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
        if (!(federationOverviewReadModelSelection != null && federationOverviewReadModelSelection.state == FederationStateEnum.Active)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.federationmanagement.inviteParticipant.federationOverview.notEligible",
                args = mapOf(
                    "command" to "Invite Participant",
                    "projection" to "Federation Overview",
                    "field" to "federationId",
                    "profile" to null
                ),
                message = "Federation Overview selection is not eligible."
            )
        }
        val organizationDirectoryReadModelSelection = organizationDirectoryReadModelRepository.findById(command.organizationId)
        if (!(organizationDirectoryReadModelSelection != null && organizationDirectoryReadModelSelection.state == OrganizationStateEnum.Active)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.federationmanagement.inviteParticipant.organizationDirectory.notEligible",
                args = mapOf(
                    "command" to "Invite Participant",
                    "projection" to "Organization Directory",
                    "field" to "organizationId",
                    "profile" to null
                ),
                message = "Organization Directory selection is not eligible."
            )
        }
        eventAppender.append(decision.decide(command))
    }
}
