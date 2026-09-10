package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterRuntimeRetryFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.FAILED) {
            "ReleaseRuntimeEngineJobAfterRuntimeRetryFailure requires RoundExecution to be Failed."
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult.Succeeded -> listOf(
            RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, runtimeEngineReleaseFailureReason = portResult.runtimeEngineReleaseFailureReason, executionPlanId = command.executionPlanId)
            )
                }
    }
}
