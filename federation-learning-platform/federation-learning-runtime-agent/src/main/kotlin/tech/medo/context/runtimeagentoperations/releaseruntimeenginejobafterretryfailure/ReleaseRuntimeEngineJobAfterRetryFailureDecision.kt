package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureCommand

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterRetryFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterRetryFailureCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterRetryFailureResult): List<Any> {
        if (state.currentState != RoundExecutionStateEnum.Failed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.releaseRuntimeEngineJobAfterRetryFailure.requiresState",
                args = mapOf(
                    "command" to "ReleaseRuntimeEngineJobAfterRetryFailure",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "Failed",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReleaseRuntimeEngineJobAfterRetryFailure requires RoundExecution to be Failed."
            )
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterRetryFailureResult.Succeeded -> listOf(
            RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, runtimeEngineReleaseFailureReason = portResult.runtimeEngineReleaseFailureReason, executionPlanId = command.executionPlanId)
            )
                }
    }
}
