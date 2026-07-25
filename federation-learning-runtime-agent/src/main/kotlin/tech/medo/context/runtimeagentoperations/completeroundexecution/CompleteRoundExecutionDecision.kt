package tech.medo.runtimeagentoperations.completeroundexecution

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.completeroundexecution.CompleteRoundExecutionCommand

import tech.medo.runtimeagentoperations.events.RoundExecutionCompletedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class CompleteRoundExecutionDecision {
    fun decide(command: CompleteRoundExecutionCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.RUNNING) {
            "CompleteRoundExecution requires RoundExecution to be Running."
        }
        return listOf(
            RoundExecutionCompletedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, runtimeEngineJobId = command.runtimeEngineJobId)
        )
    }
}
