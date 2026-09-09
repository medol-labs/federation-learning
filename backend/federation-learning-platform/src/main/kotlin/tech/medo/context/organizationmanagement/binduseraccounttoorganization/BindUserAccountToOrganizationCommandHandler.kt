package tech.medo.organizationmanagement.binduseraccounttoorganization

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.organizationmanagement.binduseraccounttoorganization.BindUserAccountToOrganizationCommand



import tech.medo.organizationmanagement.userorganizationmembership.UserOrganizationMembershipUserAccountIdOrganizationIdReservationState

@Component
class BindUserAccountToOrganizationCommandHandler(
    private val decision: BindUserAccountToOrganizationDecision
) {
    @CommandHandler
    fun handle(
        command: BindUserAccountToOrganizationCommand,
        @InjectEntity(idProperty = "userOrganizationMembershipUserAccountIdOrganizationIdSelection") userOrganizationMembershipUserAccountIdOrganizationIdReservation: UserOrganizationMembershipUserAccountIdOrganizationIdReservationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, userOrganizationMembershipUserAccountIdOrganizationIdReservation))
    }
}
