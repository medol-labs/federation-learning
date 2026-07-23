package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureCommand

import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterStartFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterStartFailureCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.RUNNING) {
            "ReleaseRuntimeEngineJobAfterStartFailure requires RoundExecution to be Running."
        }
        return listOf(
            RuntimeEngineJobReleaseFailedOrSkippedAfterStartFailureEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, failureReason = null /* TODO: derive value */)
        )
    }
}
