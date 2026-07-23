package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureCommand

import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterFailureCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.FAILED) {
            "ReleaseRuntimeEngineJobAfterFailure requires RoundExecution to be Failed."
        }
        return listOf(
            RuntimeEngineJobReleaseFailedOrSkippedEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, failureReason = null /* TODO: derive value */)
        )
    }
}
