package tech.medo.runtimeagentoperations.rejectexecutionplan

import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanCommand
import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanResult
import tech.medo.runtimeagentoperations.events.ExecutionPlanRejectedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface RejectExecutionPlanDecision {
    fun decide(command: RejectExecutionPlanCommand, state: RoundExecutionState, portResult: RejectExecutionPlanResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.PLAN_RECEIVED) {
            "RejectExecutionPlan requires RoundExecution to be PlanReceived."
        }
        return when (portResult) {
                    is RejectExecutionPlanResult.Succeeded -> listOf(ExecutionPlanRejectedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, runtimeId = command.runtimeId, localExecutionRequirementsSatisfied = command.localExecutionRequirementsSatisfied, runtimeIdentityMatched = command.runtimeIdentityMatched, runtimeDatasetBindingAvailable = command.runtimeDatasetBindingAvailable, datasetAccessValidated = command.datasetAccessValidated, baseModelAvailable = command.baseModelAvailable, trainingConfigurationSupported = command.trainingConfigurationSupported, runtimeResourceAvailable = command.runtimeResourceAvailable, runtimeAgentIdle = command.runtimeAgentIdle, rejectionReasons = command.rejectionReasons))
                }
    }
}
