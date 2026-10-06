package tech.medo.runtimegovernance.revokeruntimeidentity

import tech.medo.runtimegovernance.revokeruntimeidentity.RevokeRuntimeIdentityCommand


import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent
import tech.medo.runtimegovernance.runtimeidentity.RuntimeIdentityState


import tech.medo.runtimegovernance.domain.states.RuntimeIdentityStateEnum


interface RevokeRuntimeIdentityDecision {
    fun decide(command: RevokeRuntimeIdentityCommand, state: RuntimeIdentityState): List<Any> {
        if (state.currentState != RuntimeIdentityStateEnum.Active) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimegovernance.revokeRuntimeIdentity.requiresState",
                args = mapOf(
                    "command" to "RevokeRuntimeIdentity",
                    "aggregate" to "RuntimeIdentity",
                    "expectedState" to "Active",
                    "actualState" to state.currentState.toString()
                ),
                message = "RevokeRuntimeIdentity requires RuntimeIdentity to be Active."
            )
        }
        return listOf(
            RuntimeIdentityRevokedEvent(runtimeId = command.runtimeId, revocationReason = command.revocationReason)
        )
    }
}
