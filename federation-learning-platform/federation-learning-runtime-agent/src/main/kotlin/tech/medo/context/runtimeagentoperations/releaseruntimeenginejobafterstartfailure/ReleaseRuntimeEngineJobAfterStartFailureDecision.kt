package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureCommand

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterStartFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterStartFailureCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterStartFailureResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.START_FAILED) {
            "ReleaseRuntimeEngineJobAfterStartFailure requires RoundExecution to be StartFailed."
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterStartFailureResult.Succeeded -> listOf(
            RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, runtimeEngineReleaseFailureReason = portResult.runtimeEngineReleaseFailureReason, executionPlanId = command.executionPlanId)
            )
                }
    }
}
