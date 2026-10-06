package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand

import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


interface VerifyRuntimeInfrastructureDecision {
    fun decide(command: VerifyRuntimeInfrastructureCommand, state: RuntimeInfrastructureState, portResult: RuntimeInfrastructureVerification, now: java.time.LocalDateTime): List<Any> {
        if (state.currentState != RuntimeInfrastructureStateEnum.Prepared) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeprovisioning.verifyRuntimeInfrastructure.requiresState",
                args = mapOf(
                    "command" to "VerifyRuntimeInfrastructure",
                    "aggregate" to "RuntimeInfrastructure",
                    "expectedState" to "Prepared",
                    "actualState" to state.currentState.toString()
                ),
                message = "VerifyRuntimeInfrastructure requires RuntimeInfrastructure to be Prepared."
            )
        }
        return when (portResult) {
                    is RuntimeInfrastructureVerification.Succeeded -> listOf(
            RuntimeInfrastructureVerifiedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, runtimeAgentId = command.runtimeAgentId, agentInstallMode = portResult.agentInstallMode, expectedNodeCount = command.expectedNodeCount, observedNodeCount = portResult.observedNodeCount)
            )
                    is RuntimeInfrastructureVerification.Rejected -> listOf(RuntimeInfrastructureVerificationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, runtimeAgentId = command.runtimeAgentId, agentInstallMode = portResult.agentInstallMode, expectedNodeCount = command.expectedNodeCount, observedNodeCount = portResult.observedNodeCount, failureReason = portResult.failureReason))
                    is RuntimeInfrastructureVerification.Unavailable -> listOf(RuntimeInfrastructureVerificationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, runtimeAgentId = command.runtimeAgentId, agentInstallMode = "" /* TODO: provide agentInstallMode */, expectedNodeCount = command.expectedNodeCount, observedNodeCount = null, failureReason = portResult.failureReason))
                }
    }
}
