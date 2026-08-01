package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanCommand

import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.runtimeinstallationplan.RuntimeInstallationPlanState





interface CreateRuntimeInstallationPlanDecision {
    fun decide(command: CreateRuntimeInstallationPlanCommand): List<Any> {
        return listOf(
            RuntimeInstallationPlanCreatedEvent(runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeInfrastructureId = java.util.UUID.randomUUID() /* TODO: derive value */, organizationId = command.organizationId, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount)
        )
    }
}
