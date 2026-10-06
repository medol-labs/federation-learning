package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionCommand

import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleasedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterCompletionDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterCompletionCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterCompletionResult): List<Any> {
        if (state.currentState != RoundExecutionStateEnum.UpdateSubmitted) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.releaseRuntimeEngineJobAfterCompletion.requiresState",
                args = mapOf(
                    "command" to "ReleaseRuntimeEngineJobAfterCompletion",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "UpdateSubmitted",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReleaseRuntimeEngineJobAfterCompletion requires RoundExecution to be UpdateSubmitted."
            )
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterCompletionResult.Succeeded -> listOf(
            RuntimeEngineJobReleasedEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, executionPlanId = command.executionPlanId)
            )
                }
    }
}
