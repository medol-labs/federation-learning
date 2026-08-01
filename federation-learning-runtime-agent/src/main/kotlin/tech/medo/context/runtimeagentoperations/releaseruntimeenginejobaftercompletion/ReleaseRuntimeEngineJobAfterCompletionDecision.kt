package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionCommand
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleasedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface ReleaseRuntimeEngineJobAfterCompletionDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterCompletionCommand, state: RoundExecutionState, portResult: ReleaseRuntimeEngineJobAfterCompletionResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.COMPLETED) {
            "ReleaseRuntimeEngineJobAfterCompletion requires RoundExecution to be Completed."
        }
        return when (portResult) {
                    is ReleaseRuntimeEngineJobAfterCompletionResult.Succeeded -> listOf(RuntimeEngineJobReleasedEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, executionPlanId = command.executionPlanId))
                }
    }
}
