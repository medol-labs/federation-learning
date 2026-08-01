package tech.medo.organizationmanagement.activateorganization

import tech.medo.organizationmanagement.activateorganization.ActivateOrganizationCommand

import tech.medo.organizationmanagement.events.OrganizationActivatedEvent
import tech.medo.organizationmanagement.organization.OrganizationState


import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


interface ActivateOrganizationDecision {
    fun decide(command: ActivateOrganizationCommand, state: OrganizationState): List<Any> {
        require(state.currentState == OrganizationStateEnum.REGISTERED) {
            "ActivateOrganization requires Organization to be Registered."
        }
        return listOf(
            OrganizationActivatedEvent(organizationId = command.organizationId, activationNote = command.activationNote)
        )
    }
}
