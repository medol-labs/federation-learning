package tech.medo.runtimeprovisioning.registerruntimeinfrastructure

import tech.medo.runtimeprovisioning.registerruntimeinfrastructure.RegisterRuntimeInfrastructureCommand


import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


interface RegisterRuntimeInfrastructureDecision {
    fun decide(command: RegisterRuntimeInfrastructureCommand, state: RuntimeInfrastructureState): List<Any> {
        require(state.currentState == RuntimeInfrastructureStateEnum.PLANNED) {
            "RegisterRuntimeInfrastructure requires RuntimeInfrastructure to be Planned."
        }
        return listOf(
            RuntimeInfrastructureRegisteredEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeAgentId = command.runtimeAgentId)
        )
    }
}
