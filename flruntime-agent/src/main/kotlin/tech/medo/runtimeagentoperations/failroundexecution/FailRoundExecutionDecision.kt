package tech.medo.runtimeagentoperations.failroundexecution

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.failroundexecution.FailRoundExecutionCommand

import tech.medo.runtimeagentoperations.events.RoundExecutionFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class FailRoundExecutionDecision {
    fun decide(command: FailRoundExecutionCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.RUNNING) {
            "FailRoundExecution requires RoundExecution to be Running."
        }
        return listOf(
            RoundExecutionFailedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, runtimeEngineJobId = command.runtimeEngineJobId, failureReason = command.failureReason)
        )
    }
}
