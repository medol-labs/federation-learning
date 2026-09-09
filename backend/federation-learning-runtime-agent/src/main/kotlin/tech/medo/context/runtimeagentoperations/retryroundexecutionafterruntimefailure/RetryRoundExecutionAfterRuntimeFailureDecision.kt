package tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure

import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureCommand

import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureResult
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface RetryRoundExecutionAfterRuntimeFailureDecision {
    fun decide(command: RetryRoundExecutionAfterRuntimeFailureCommand, state: RoundExecutionState, portResult: RetryRoundExecutionAfterRuntimeFailureResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.FAILED) {
            "RetryRoundExecutionAfterRuntimeFailure requires RoundExecution to be Failed."
        }
        return when (portResult) {
                    is RetryRoundExecutionAfterRuntimeFailureResult.Succeeded -> listOf(RoundExecutionRuntimeRetryStartedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, runtimeEngineJobId = command.runtimeEngineJobId, retryReason = command.retryReason))
                }
    }
}
