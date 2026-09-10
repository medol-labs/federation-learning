package tech.medo.runtimeprovisioning.planruntimeinfrastructure

import tech.medo.runtimeprovisioning.planruntimeinfrastructure.PlanRuntimeInfrastructureCommand


import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePlannedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState





interface PlanRuntimeInfrastructureDecision {
    fun decide(command: PlanRuntimeInfrastructureCommand): List<Any> {
        return listOf(
            RuntimeInfrastructurePlannedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount)
        )
    }
}
