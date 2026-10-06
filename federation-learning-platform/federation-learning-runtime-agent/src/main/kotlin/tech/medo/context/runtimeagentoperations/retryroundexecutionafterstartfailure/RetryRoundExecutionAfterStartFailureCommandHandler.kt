package tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureCommand
import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureInput
import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureService


import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class RetryRoundExecutionAfterStartFailureCommandHandler(
    private val decision: RetryRoundExecutionAfterStartFailureDecision,
    private val retryRoundExecutionAfterStartFailureService: RetryRoundExecutionAfterStartFailureService
) {
    @CommandHandler
    fun handle(
        command: RetryRoundExecutionAfterStartFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
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
        val input = RetryRoundExecutionAfterStartFailureInput(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, runtimeEngineJobId = command.runtimeEngineJobId, retryReason = command.retryReason)
        val portResult = retryRoundExecutionAfterStartFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
