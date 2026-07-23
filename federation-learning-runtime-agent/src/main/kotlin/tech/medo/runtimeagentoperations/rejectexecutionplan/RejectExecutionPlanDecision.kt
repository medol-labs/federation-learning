package tech.medo.runtimeagentoperations.rejectexecutionplan

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanCommand

import tech.medo.runtimeagentoperations.events.ExecutionPlanRejectedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class RejectExecutionPlanDecision {
    fun decide(command: RejectExecutionPlanCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.PLAN_RECEIVED) {
            "RejectExecutionPlan requires RoundExecution to be PlanReceived."
        }
        return listOf(
            ExecutionPlanRejectedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, runtimeId = command.runtimeId, localExecutionRequirementsSatisfied = command.localExecutionRequirementsSatisfied, runtimeIdentityMatched = command.runtimeIdentityMatched, runtimeDatasetBindingAvailable = command.runtimeDatasetBindingAvailable, datasetAccessValidated = command.datasetAccessValidated, baseModelAvailable = command.baseModelAvailable, trainingConfigurationSupported = command.trainingConfigurationSupported, runtimeResourceAvailable = command.runtimeResourceAvailable, runtimeAgentIdle = command.runtimeAgentIdle, rejectionReasons = command.rejectionReasons)
        )
    }
}
