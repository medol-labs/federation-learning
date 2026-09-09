package tech.medo.organizationmanagement.deactivateorganization

import tech.medo.organizationmanagement.deactivateorganization.DeactivateOrganizationCommand


import tech.medo.organizationmanagement.events.OrganizationDeactivatedEvent
import tech.medo.organizationmanagement.organization.OrganizationState


import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


interface DeactivateOrganizationDecision {
    fun decide(command: DeactivateOrganizationCommand, state: OrganizationState): List<Any> {
        require(state.currentState == OrganizationStateEnum.ACTIVE) {
            "DeactivateOrganization requires Organization to be Active."
        }
        return listOf(
            OrganizationDeactivatedEvent(organizationId = command.organizationId, deactivationReason = command.deactivationReason)
        )
    }
}
