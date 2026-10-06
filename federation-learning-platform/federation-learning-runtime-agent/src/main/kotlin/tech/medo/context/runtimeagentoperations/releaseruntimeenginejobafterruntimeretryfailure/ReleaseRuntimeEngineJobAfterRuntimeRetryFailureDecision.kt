package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterRuntimeRetryFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult): List<Any> {
        if (state.currentState != RoundExecutionStateEnum.Failed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.releaseRuntimeEngineJobAfterRuntimeRetryFailure.requiresState",
                args = mapOf(
                    "command" to "ReleaseRuntimeEngineJobAfterRuntimeRetryFailure",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "Failed",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReleaseRuntimeEngineJobAfterRuntimeRetryFailure requires RoundExecution to be Failed."
            )
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult.Succeeded -> listOf(
            RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, runtimeEngineReleaseFailureReason = portResult.runtimeEngineReleaseFailureReason, executionPlanId = command.executionPlanId)
            )
                }
    }
}
