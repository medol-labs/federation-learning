package tech.medo.organizationmanagement.binduseraccounttoorganization

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.organizationmanagement.binduseraccounttoorganization.BindUserAccountToOrganizationCommand

import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelRepository
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


import tech.medo.organizationmanagement.userorganizationmembership.UserOrganizationMembershipUserAccountIdOrganizationIdReservationState

@Component
class BindUserAccountToOrganizationCommandHandler(
    private val decision: BindUserAccountToOrganizationDecision,
    private val organizationDirectoryReadModelRepository: OrganizationDirectoryReadModelRepository
) {
    @CommandHandler
    fun handle(
        command: BindUserAccountToOrganizationCommand,
        @InjectEntity(idProperty = "userOrganizationMembershipUserAccountIdOrganizationIdSelection") userOrganizationMembershipUserAccountIdOrganizationIdReservation: UserOrganizationMembershipUserAccountIdOrganizationIdReservationState,
        eventAppender: EventAppender
    ) {
        val organizationDirectoryReadModelSelection = organizationDirectoryReadModelRepository.findById(command.organizationId)
        require(organizationDirectoryReadModelSelection != null && organizationDirectoryReadModelSelection.state == OrganizationStateEnum.Active) {
            "Organization Directory selection is not eligible."
        }
        eventAppender.append(decision.decide(command, userOrganizationMembershipUserAccountIdOrganizationIdReservation))
    }
}
