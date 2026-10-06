package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureCommand

import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterFailureCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterFailureResult): List<Any> {
        if (state.currentState != RoundExecutionStateEnum.Failed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.releaseRuntimeEngineJobAfterFailure.requiresState",
                args = mapOf(
                    "command" to "ReleaseRuntimeEngineJobAfterFailure",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "Failed",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReleaseRuntimeEngineJobAfterFailure requires RoundExecution to be Failed."
            )
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterFailureResult.Succeeded -> listOf(
            RuntimeEngineJobReleaseFailedOrSkippedEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, runtimeEngineReleaseFailureReason = portResult.runtimeEngineReleaseFailureReason, executionPlanId = command.executionPlanId)
            )
                }
    }
}
