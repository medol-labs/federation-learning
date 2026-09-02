package tech.medo.organizationmanagement.reactivateorganization

import tech.medo.organizationmanagement.reactivateorganization.ReactivateOrganizationCommand


import tech.medo.organizationmanagement.events.OrganizationReactivatedEvent
import tech.medo.organizationmanagement.organization.OrganizationState


import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


interface ReactivateOrganizationDecision {
    fun decide(command: ReactivateOrganizationCommand, state: OrganizationState): List<Any> {
        require(state.currentState == OrganizationStateEnum.DEACTIVATED) {
            "ReactivateOrganization requires Organization to be Deactivated."
        }
        return listOf(
            OrganizationReactivatedEvent(organizationId = command.organizationId, reactivationReason = command.reactivationReason)
        )
    }
}
