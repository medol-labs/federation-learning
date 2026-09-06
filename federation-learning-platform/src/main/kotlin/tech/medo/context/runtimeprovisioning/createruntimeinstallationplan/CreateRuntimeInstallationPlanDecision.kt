package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanCommand

import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanResult
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.runtimeinstallationplan.RuntimeInstallationPlanState





interface CreateRuntimeInstallationPlanDecision {
    fun decide(command: CreateRuntimeInstallationPlanCommand, portResult: CreateRuntimeInstallationPlanResult): List<Any> {
        return when (portResult) {
                    is CreateRuntimeInstallationPlanResult.Succeeded -> listOf(RuntimeInstallationPlanCreatedEvent(runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeInfrastructureId = command.runtimeInfrastructureId, organizationId = command.organizationId, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeName = command.runtimeName, bootstrapCommand = portResult.bootstrapCommand, nodeLabelCommand = portResult.nodeLabelCommand, nodeTaintCommand = portResult.nodeTaintCommand, runtimeAgentNodeSelectorYaml = portResult.runtimeAgentNodeSelectorYaml, runtimeAgentTolerationsYaml = portResult.runtimeAgentTolerationsYaml, bootstrapConfigYaml = portResult.bootstrapConfigYaml, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount))
                }
    }
}
