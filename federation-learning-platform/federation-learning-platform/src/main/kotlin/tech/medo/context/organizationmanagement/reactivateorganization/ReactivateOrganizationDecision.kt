package tech.medo.organizationmanagement.reactivateorganization

import tech.medo.organizationmanagement.reactivateorganization.ReactivateOrganizationCommand


import tech.medo.organizationmanagement.events.OrganizationReactivatedEvent
import tech.medo.organizationmanagement.organization.OrganizationState


import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


interface ReactivateOrganizationDecision {
    fun decide(command: ReactivateOrganizationCommand, state: OrganizationState): List<Any> {
        if (state.currentState != OrganizationStateEnum.Deactivated) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.organizationmanagement.reactivateOrganization.requiresState",
                args = mapOf(
                    "command" to "ReactivateOrganization",
                    "aggregate" to "Organization",
                    "expectedState" to "Deactivated",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReactivateOrganization requires Organization to be Deactivated."
            )
        }
        return listOf(
            OrganizationReactivatedEvent(organizationId = command.organizationId, organizationName = command.organizationName, reactivationReason = command.reactivationReason)
        )
    }
}
