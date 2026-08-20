package tech.medo.organizationmanagement.reactivateorganization

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.organizationmanagement.reactivateorganization.ReactivateOrganizationCommand

import tech.medo.organizationmanagement.organization.OrganizationState




@Component
class ReactivateOrganizationCommandHandler(
    private val decision: ReactivateOrganizationDecision
) {
    @CommandHandler
    fun handle(
        command: ReactivateOrganizationCommand,
        @InjectEntity(idProperty = "organizationName") state: OrganizationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
