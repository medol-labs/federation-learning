package tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure

import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureCommand

import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureResult
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface RetryRoundExecutionAfterRuntimeFailureDecision {
    fun decide(command: RetryRoundExecutionAfterRuntimeFailureCommand, state: RoundExecutionState, portResult: RetryRoundExecutionAfterRuntimeFailureResult): List<Any> {
        if (state.currentState != RoundExecutionStateEnum.Failed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.retryRoundExecutionAfterRuntimeFailure.requiresState",
                args = mapOf(
                    "command" to "RetryRoundExecutionAfterRuntimeFailure",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "Failed",
                    "actualState" to state.currentState.toString()
                ),
                message = "RetryRoundExecutionAfterRuntimeFailure requires RoundExecution to be Failed."
            )
        }
        return when (portResult) {
                    is RetryRoundExecutionAfterRuntimeFailureResult.Succeeded -> listOf(
            RoundExecutionRuntimeRetryStartedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, runtimeEngineJobId = command.runtimeEngineJobId, retryReason = command.retryReason)
            )
                }
    }
}
