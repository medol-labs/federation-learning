package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureCommand
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterFailureCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterFailureResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.FAILED) {
            "ReleaseRuntimeEngineJobAfterFailure requires RoundExecution to be Failed."
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterFailureResult.Succeeded -> listOf(RuntimeEngineJobReleaseFailedOrSkippedEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, failureReason = portResult.failureReason, executionPlanId = command.executionPlanId))
                }
    }
}
