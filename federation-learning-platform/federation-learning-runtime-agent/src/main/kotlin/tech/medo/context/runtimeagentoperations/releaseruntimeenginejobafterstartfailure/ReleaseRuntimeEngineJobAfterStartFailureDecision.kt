package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureCommand

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterStartFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterStartFailureCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterStartFailureResult): List<Any> {
        if (state.currentState != RoundExecutionStateEnum.StartFailed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.releaseRuntimeEngineJobAfterStartFailure.requiresState",
                args = mapOf(
                    "command" to "ReleaseRuntimeEngineJobAfterStartFailure",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "StartFailed",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReleaseRuntimeEngineJobAfterStartFailure requires RoundExecution to be StartFailed."
            )
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterStartFailureResult.Succeeded -> listOf(
            RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, runtimeEngineReleaseFailureReason = portResult.runtimeEngineReleaseFailureReason, executionPlanId = command.executionPlanId)
            )
                }
    }
}
