package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState


import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


@Component
class VerifyRuntimeInfrastructureDecision {
    fun decide(command: VerifyRuntimeInfrastructureCommand, state: RuntimeInfrastructureState, portResult: RuntimeInfrastructureVerification, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == RuntimeInfrastructureStateEnum.REGISTERED) {
            "VerifyRuntimeInfrastructure requires RuntimeInfrastructure to be Registered."
        }
        return when (portResult) {
                    is RuntimeInfrastructureVerification.Succeeded -> listOf(RuntimeInfrastructureVerifiedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, agentInstallMode = command.agentInstallMode, observedNodeCount = command.observedNodeCount))
                    is RuntimeInfrastructureVerification.Rejected -> listOf(RuntimeInfrastructureVerificationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, observedNodeCount = command.observedNodeCount, failureReason = portResult.failureReason))
                    is RuntimeInfrastructureVerification.Unavailable -> listOf(RuntimeInfrastructureVerificationFailedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, observedNodeCount = command.observedNodeCount, failureReason = portResult.failureReason))
                }
    }
}
