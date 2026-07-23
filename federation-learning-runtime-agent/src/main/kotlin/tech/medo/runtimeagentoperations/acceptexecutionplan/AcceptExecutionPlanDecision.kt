package tech.medo.runtimeagentoperations.acceptexecutionplan

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanCommand

import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class AcceptExecutionPlanDecision {
    fun decide(command: AcceptExecutionPlanCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.PLAN_RECEIVED) {
            "AcceptExecutionPlan requires RoundExecution to be PlanReceived."
        }
        return listOf(
            ExecutionPlanAcceptedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, runtimeId = command.runtimeId, localExecutionRequirementsSatisfied = command.localExecutionRequirementsSatisfied, runtimeIdentityMatched = command.runtimeIdentityMatched, runtimeDatasetBindingAvailable = command.runtimeDatasetBindingAvailable, datasetAccessValidated = command.datasetAccessValidated, baseModelAvailable = command.baseModelAvailable, trainingConfigurationSupported = command.trainingConfigurationSupported, runtimeResourceAvailable = command.runtimeResourceAvailable, runtimeAgentIdle = command.runtimeAgentIdle)
        )
    }
}
