package tech.medo.runtimeprovisioning.deployruntimeagent

import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentCommand

import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


interface DeployRuntimeAgentDecision {
    fun decide(command: DeployRuntimeAgentCommand, state: RuntimeInfrastructureState, portResult: DeployRuntimeAgentResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == RuntimeInfrastructureStateEnum.VERIFIED) {
            "DeployRuntimeAgent requires RuntimeInfrastructure to be Verified."
        }
        return when (portResult) {
                    is DeployRuntimeAgentResult.Succeeded -> listOf(RuntimeAgentInstallationSucceededEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeAgentId = command.runtimeAgentId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, agentVersion = portResult.agentVersion))
                    is DeployRuntimeAgentResult.Rejected -> listOf(RuntimeAgentInstallationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeAgentId = command.runtimeAgentId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, failureReason = portResult.failureReason))
                    is DeployRuntimeAgentResult.Unavailable -> listOf(RuntimeAgentInstallationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeAgentId = command.runtimeAgentId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, failureReason = portResult.failureReason))
                }
    }
}
