package tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification

import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationCommand

import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationResult
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationRetryFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState





interface RetryRuntimeInfrastructureVerificationDecision {
    fun decide(command: RetryRuntimeInfrastructureVerificationCommand, state: RuntimeInfrastructureState, portResult: RetryRuntimeInfrastructureVerificationResult, now: java.time.LocalDateTime): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is RetryRuntimeInfrastructureVerificationResult.Succeeded -> listOf(
            RuntimeInfrastructureVerificationRetrySucceededEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, runtimeAgentId = command.runtimeAgentId, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, observedNodeCount = portResult.observedNodeCount, retryReason = command.retryReason)
            )
                    is RetryRuntimeInfrastructureVerificationResult.Rejected -> listOf(RuntimeInfrastructureVerificationRetryFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, runtimeAgentId = command.runtimeAgentId, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, observedNodeCount = portResult.observedNodeCount, retryReason = command.retryReason, failureReason = portResult.failureReason))
                    is RetryRuntimeInfrastructureVerificationResult.Unavailable -> listOf(RuntimeInfrastructureVerificationRetryFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, runtimeAgentId = command.runtimeAgentId, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, observedNodeCount = null, retryReason = command.retryReason, failureReason = portResult.failureReason))
                }
    }
}
