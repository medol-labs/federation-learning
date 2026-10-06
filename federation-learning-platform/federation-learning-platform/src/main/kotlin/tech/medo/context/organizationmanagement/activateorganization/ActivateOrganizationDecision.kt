package tech.medo.organizationmanagement.activateorganization

import tech.medo.organizationmanagement.activateorganization.ActivateOrganizationCommand


import tech.medo.organizationmanagement.events.OrganizationActivatedEvent
import tech.medo.organizationmanagement.organization.OrganizationState


import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


interface ActivateOrganizationDecision {
    fun decide(command: ActivateOrganizationCommand, state: OrganizationState): List<Any> {
        if (state.currentState != OrganizationStateEnum.Registered) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.organizationmanagement.activateOrganization.requiresState",
                args = mapOf(
                    "command" to "ActivateOrganization",
                    "aggregate" to "Organization",
                    "expectedState" to "Registered",
                    "actualState" to state.currentState.toString()
                ),
                message = "ActivateOrganization requires Organization to be Registered."
            )
        }
        return listOf(
            OrganizationActivatedEvent(organizationId = command.organizationId, organizationName = command.organizationName, activationNote = command.activationNote)
        )
    }
}
