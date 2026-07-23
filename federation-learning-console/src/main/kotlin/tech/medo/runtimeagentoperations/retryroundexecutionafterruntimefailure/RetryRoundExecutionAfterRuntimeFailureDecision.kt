package tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureCommand

import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class RetryRoundExecutionAfterRuntimeFailureDecision {
    fun decide(command: RetryRoundExecutionAfterRuntimeFailureCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.FAILED) {
            "RetryRoundExecutionAfterRuntimeFailure requires RoundExecution to be Failed."
        }
        return listOf(
            RoundExecutionRuntimeRetryStartedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelVersionId = command.baseModelVersionId, runtimeEngineJobId = command.runtimeEngineJobId, retryReason = command.retryReason)
        )
    }
}
