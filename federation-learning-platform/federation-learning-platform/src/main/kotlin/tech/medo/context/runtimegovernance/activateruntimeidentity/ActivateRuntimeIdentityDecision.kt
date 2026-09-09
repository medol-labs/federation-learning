package tech.medo.runtimegovernance.activateruntimeidentity

import tech.medo.runtimegovernance.activateruntimeidentity.ActivateRuntimeIdentityCommand


import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.runtimeidentity.RuntimeIdentityState





interface ActivateRuntimeIdentityDecision {
    fun decide(command: ActivateRuntimeIdentityCommand): List<Any> {
        return listOf(
            RuntimeIdentityActivatedEvent(runtimeId = command.runtimeId, runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, organizationId = command.organizationId, runtimeName = command.runtimeName)
        )
    }
}
