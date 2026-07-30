package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand

import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterRuntimeRetryFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.RETRIED) {
            "ReleaseRuntimeEngineJobAfterRuntimeRetryFailure requires RoundExecution to be Retried."
        }
        return listOf(
            RuntimeEngineJobReleaseFailedOrSkippedAfterRuntimeRetryEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, failureReason = null /* TODO: derive value */, executionPlanId = command.executionPlanId)
        )
    }
}
