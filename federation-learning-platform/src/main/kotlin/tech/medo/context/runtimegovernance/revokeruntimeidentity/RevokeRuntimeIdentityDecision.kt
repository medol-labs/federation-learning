package tech.medo.runtimegovernance.revokeruntimeidentity

import org.springframework.stereotype.Component
import tech.medo.runtimegovernance.revokeruntimeidentity.RevokeRuntimeIdentityCommand

import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent
import tech.medo.runtimegovernance.runtimeidentity.RuntimeIdentityState


import tech.medo.runtimegovernance.domain.states.RuntimeIdentityStateEnum


@Component
class RevokeRuntimeIdentityDecision {
    fun decide(command: RevokeRuntimeIdentityCommand, state: RuntimeIdentityState): List<Any> {
        require(state.currentState == RuntimeIdentityStateEnum.ACTIVE) {
            "RevokeRuntimeIdentity requires RuntimeIdentity to be Active."
        }
        return listOf(
            RuntimeIdentityRevokedEvent(runtimeId = command.runtimeId, revocationReason = command.revocationReason)
        )
    }
}
