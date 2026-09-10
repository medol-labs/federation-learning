package tech.medo.runtimeprovisioning.confirmruntimeinfrastructureprepared

import tech.medo.runtimeprovisioning.confirmruntimeinfrastructureprepared.ConfirmRuntimeInfrastructurePreparedCommand


import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePreparedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


interface ConfirmRuntimeInfrastructurePreparedDecision {
    fun decide(command: ConfirmRuntimeInfrastructurePreparedCommand, state: RuntimeInfrastructureState): List<Any> {
        require(state.currentState == RuntimeInfrastructureStateEnum.REGISTERED) {
            "ConfirmRuntimeInfrastructurePrepared requires RuntimeInfrastructure to be Registered."
        }
        return listOf(
            RuntimeInfrastructurePreparedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, runtimeAgentId = command.runtimeAgentId, preparedNodeCount = command.preparedNodeCount, preparationNotes = command.preparationNotes)
        )
    }
}
