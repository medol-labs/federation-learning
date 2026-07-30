package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureCommand

import tech.medo.runtimeagentoperations.events.RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterRetryFailureDecision {
    fun decide(command: ReleaseRuntimeEngineJobAfterRetryFailureCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.RETRIED) {
            "ReleaseRuntimeEngineJobAfterRetryFailure requires RoundExecution to be Retried."
        }
        return listOf(
            RuntimeEngineJobReleaseFailedOrSkippedAfterRetryEvent(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId, failureReason = null /* TODO: derive value */, executionPlanId = command.executionPlanId)
        )
    }
}
