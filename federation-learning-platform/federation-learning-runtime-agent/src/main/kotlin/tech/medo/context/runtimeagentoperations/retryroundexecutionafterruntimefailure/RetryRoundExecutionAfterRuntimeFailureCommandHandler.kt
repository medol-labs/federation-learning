package tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureCommand
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureInput
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureService


import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class RetryRoundExecutionAfterRuntimeFailureCommandHandler(
    private val decision: RetryRoundExecutionAfterRuntimeFailureDecision,
    private val retryRoundExecutionAfterRuntimeFailureService: RetryRoundExecutionAfterRuntimeFailureService
) {
    @CommandHandler
    fun handle(
        command: RetryRoundExecutionAfterRuntimeFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
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
        val input = RetryRoundExecutionAfterRuntimeFailureInput(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, runtimeEngineJobId = command.runtimeEngineJobId, retryReason = command.retryReason)
        val portResult = retryRoundExecutionAfterRuntimeFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
