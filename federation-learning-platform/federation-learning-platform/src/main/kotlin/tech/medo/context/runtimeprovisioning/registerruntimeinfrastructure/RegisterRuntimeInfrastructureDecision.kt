package tech.medo.runtimeprovisioning.registerruntimeinfrastructure

import tech.medo.runtimeprovisioning.registerruntimeinfrastructure.RegisterRuntimeInfrastructureCommand


import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


interface RegisterRuntimeInfrastructureDecision {
    fun decide(command: RegisterRuntimeInfrastructureCommand, state: RuntimeInfrastructureState): List<Any> {
        if (state.currentState != RuntimeInfrastructureStateEnum.Planned) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeprovisioning.registerRuntimeInfrastructure.requiresState",
                args = mapOf(
                    "command" to "RegisterRuntimeInfrastructure",
                    "aggregate" to "RuntimeInfrastructure",
                    "expectedState" to "Planned",
                    "actualState" to state.currentState.toString()
                ),
                message = "RegisterRuntimeInfrastructure requires RuntimeInfrastructure to be Planned."
            )
        }
        return listOf(
            RuntimeInfrastructureRegisteredEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, runtimeAgentId = command.runtimeAgentId)
        )
    }
}
