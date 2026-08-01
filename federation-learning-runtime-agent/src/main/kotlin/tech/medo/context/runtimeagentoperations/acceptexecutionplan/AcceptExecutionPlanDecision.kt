package tech.medo.runtimeagentoperations.acceptexecutionplan

import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanCommand
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanResult
import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface AcceptExecutionPlanDecision {
    fun decide(command: AcceptExecutionPlanCommand, state: RoundExecutionState, portResult: AcceptExecutionPlanResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.PLAN_RECEIVED) {
            "AcceptExecutionPlan requires RoundExecution to be PlanReceived."
        }
        return when (portResult) {
                    is AcceptExecutionPlanResult.Succeeded -> listOf(ExecutionPlanAcceptedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, runtimeId = command.runtimeId, localExecutionRequirementsSatisfied = command.localExecutionRequirementsSatisfied, runtimeIdentityMatched = command.runtimeIdentityMatched, runtimeDatasetBindingAvailable = command.runtimeDatasetBindingAvailable, datasetAccessValidated = command.datasetAccessValidated, baseModelAvailable = command.baseModelAvailable, trainingConfigurationSupported = command.trainingConfigurationSupported, runtimeResourceAvailable = command.runtimeResourceAvailable, runtimeAgentIdle = command.runtimeAgentIdle))
                }
    }
}
