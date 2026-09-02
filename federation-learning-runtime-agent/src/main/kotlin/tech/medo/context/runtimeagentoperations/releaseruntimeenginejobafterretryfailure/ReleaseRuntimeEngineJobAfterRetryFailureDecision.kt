package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureCommand

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterRetryFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterRetryFailureCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterRetryFailureResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.FAILED) {
            "ReleaseRuntimeEngineJobAfterRetryFailure requires RoundExecution to be Failed."
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterRetryFailureResult.Succeeded -> listOf(RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, runtimeEngineReleaseFailureReason = portResult.runtimeEngineReleaseFailureReason, executionPlanId = command.executionPlanId))
                }
    }
}
