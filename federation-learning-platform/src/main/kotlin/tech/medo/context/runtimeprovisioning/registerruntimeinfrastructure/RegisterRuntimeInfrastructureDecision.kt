package tech.medo.runtimeprovisioning.registerruntimeinfrastructure

import tech.medo.runtimeprovisioning.registerruntimeinfrastructure.RegisterRuntimeInfrastructureCommand

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState





interface RegisterRuntimeInfrastructureDecision {
    fun decide(command: RegisterRuntimeInfrastructureCommand): List<Any> {
        return listOf(
            RuntimeInfrastructureRegisteredEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId)
        )
    }
}
