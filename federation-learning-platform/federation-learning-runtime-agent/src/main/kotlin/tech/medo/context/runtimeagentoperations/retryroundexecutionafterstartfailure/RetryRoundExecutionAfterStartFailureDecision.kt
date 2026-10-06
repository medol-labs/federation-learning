package tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure

import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureCommand

import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureResult
import tech.medo.runtimeagentoperations.events.RoundExecutionStartRetryStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartRetryFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface RetryRoundExecutionAfterStartFailureDecision {
    fun decide(command: RetryRoundExecutionAfterStartFailureCommand, state: RoundExecutionState, portResult: RetryRoundExecutionAfterStartFailureResult): List<Any> {
        if (state.currentState != RoundExecutionStateEnum.StartFailed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.retryRoundExecutionAfterStartFailure.requiresState",
                args = mapOf(
                    "command" to "RetryRoundExecutionAfterStartFailure",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "StartFailed",
                    "actualState" to state.currentState.toString()
                ),
                message = "RetryRoundExecutionAfterStartFailure requires RoundExecution to be StartFailed."
            )
        }
        return when (portResult) {
                    is RetryRoundExecutionAfterStartFailureResult.Succeeded -> listOf(
            RoundExecutionStartRetryStartedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, runtimeEngineJobId = command.runtimeEngineJobId, retryReason = command.retryReason)
            )
                }
    }
}
