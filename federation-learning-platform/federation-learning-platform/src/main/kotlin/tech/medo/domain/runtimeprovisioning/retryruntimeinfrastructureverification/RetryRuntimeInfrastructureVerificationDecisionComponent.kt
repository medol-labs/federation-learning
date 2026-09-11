package tech.medo.domain.runtimeprovisioning.retryruntimeinfrastructureverification

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationCommand
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationDecision
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationResult
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState
import java.time.LocalDateTime

@Component
class RetryRuntimeInfrastructureVerificationDecisionComponent : RetryRuntimeInfrastructureVerificationDecision {
    override fun decide(
        command: RetryRuntimeInfrastructureVerificationCommand,
        state: RuntimeInfrastructureState,
        portResult: RetryRuntimeInfrastructureVerificationResult,
        now: LocalDateTime
    ): List<Any> {
        require(state.currentState == RuntimeInfrastructureStateEnum.VERIFICATION_FAILED) {
            "Runtime Infrastructure Verification Retry is allowed only when RuntimeInfrastructure is VerificationFailed."
        }
        require(command.currentRuntimeInfrastructureState == RuntimeInfrastructureStateEnum.VERIFICATION_FAILED) {
            "Runtime Infrastructure Verification Retry command state must be VerificationFailed."
        }
        return super.decide(command, state, portResult, now)
    }
}
