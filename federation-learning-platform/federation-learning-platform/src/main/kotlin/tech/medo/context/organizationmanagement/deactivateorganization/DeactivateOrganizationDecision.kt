package tech.medo.organizationmanagement.deactivateorganization

import tech.medo.organizationmanagement.deactivateorganization.DeactivateOrganizationCommand


import tech.medo.organizationmanagement.events.OrganizationDeactivatedEvent
import tech.medo.organizationmanagement.organization.OrganizationState


import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


interface DeactivateOrganizationDecision {
    fun decide(command: DeactivateOrganizationCommand, state: OrganizationState): List<Any> {
        if (state.currentState != OrganizationStateEnum.Active) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.organizationmanagement.deactivateOrganization.requiresState",
                args = mapOf(
                    "command" to "DeactivateOrganization",
                    "aggregate" to "Organization",
                    "expectedState" to "Active",
                    "actualState" to state.currentState.toString()
                ),
                message = "DeactivateOrganization requires Organization to be Active."
            )
        }
        return listOf(
            OrganizationDeactivatedEvent(organizationId = command.organizationId, organizationName = command.organizationName, deactivationReason = command.deactivationReason)
        )
    }
}
