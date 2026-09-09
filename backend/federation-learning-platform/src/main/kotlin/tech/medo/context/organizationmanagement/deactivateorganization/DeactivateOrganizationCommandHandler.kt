package tech.medo.organizationmanagement.deactivateorganization

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.organizationmanagement.deactivateorganization.DeactivateOrganizationCommand

import tech.medo.organizationmanagement.organization.OrganizationState



@Component
class DeactivateOrganizationCommandHandler(
    private val decision: DeactivateOrganizationDecision
) {
    @CommandHandler
    fun handle(
        command: DeactivateOrganizationCommand,
        @InjectEntity(idProperty = "organizationName") state: OrganizationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
