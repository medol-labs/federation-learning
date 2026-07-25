package tech.medo.organizationmanagement.activateorganization

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.organizationmanagement.activateorganization.ActivateOrganizationCommand

import tech.medo.organizationmanagement.organization.OrganizationState



@Component
class ActivateOrganizationCommandHandler(
    private val decision: ActivateOrganizationDecision
) {
    @CommandHandler
    fun handle(
        command: ActivateOrganizationCommand,
        @InjectEntity(idProperty = "organizationId") state: OrganizationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
