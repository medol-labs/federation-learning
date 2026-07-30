package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionCommand

import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleasedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterCompletionDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterCompletionCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.COMPLETED) {
            "ReleaseRuntimeEngineJobAfterCompletion requires RoundExecution to be Completed."
        }
        return listOf(
            RuntimeEngineJobReleasedEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, executionPlanId = command.executionPlanId)
        )
    }
}
