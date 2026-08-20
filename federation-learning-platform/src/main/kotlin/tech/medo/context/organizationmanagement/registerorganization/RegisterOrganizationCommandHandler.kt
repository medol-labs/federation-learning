package tech.medo.organizationmanagement.registerorganization

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.organizationmanagement.registerorganization.RegisterOrganizationCommand



import tech.medo.organizationmanagement.organization.OrganizationNameReservationState

@Component
class RegisterOrganizationCommandHandler(
    private val decision: RegisterOrganizationDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterOrganizationCommand,
        @InjectEntity(idProperty = "organizationNameSelection") organizationNameReservation: OrganizationNameReservationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, organizationNameReservation))
    }
}
