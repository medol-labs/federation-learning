package tech.medo.runtimeagentoperations.startroundexecution

import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionCommand
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionResult
import tech.medo.runtimeagentoperations.events.RoundExecutionStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface StartRoundExecutionDecision {
    fun decide(command: StartRoundExecutionCommand, state: RoundExecutionState, portResult: StartRoundExecutionResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.PLAN_ACCEPTED) {
            "StartRoundExecution requires RoundExecution to be PlanAccepted."
        }
        return when (portResult) {
                    is StartRoundExecutionResult.Succeeded -> listOf(RoundExecutionStartedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelVersionId = command.baseModelVersionId, runtimeEngineJobId = command.runtimeEngineJobId))
                }
    }
}
