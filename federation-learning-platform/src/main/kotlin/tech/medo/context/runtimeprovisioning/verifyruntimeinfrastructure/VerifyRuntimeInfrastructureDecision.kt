package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand

import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


interface VerifyRuntimeInfrastructureDecision {
    fun decide(command: VerifyRuntimeInfrastructureCommand, state: RuntimeInfrastructureState, portResult: RuntimeInfrastructureVerification, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == RuntimeInfrastructureStateEnum.PREPARED) {
            "VerifyRuntimeInfrastructure requires RuntimeInfrastructure to be Prepared."
        }
        return when (portResult) {
                    is RuntimeInfrastructureVerification.Succeeded -> listOf(RuntimeInfrastructureVerifiedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeAgentId = command.runtimeAgentId, agentInstallMode = portResult.agentInstallMode, observedNodeCount = portResult.observedNodeCount))
                    is RuntimeInfrastructureVerification.Rejected -> listOf(RuntimeInfrastructureVerificationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, observedNodeCount = portResult.observedNodeCount, failureReason = portResult.failureReason))
                    is RuntimeInfrastructureVerification.Unavailable -> listOf(RuntimeInfrastructureVerificationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, observedNodeCount = null, failureReason = portResult.failureReason))
                }
    }
}
