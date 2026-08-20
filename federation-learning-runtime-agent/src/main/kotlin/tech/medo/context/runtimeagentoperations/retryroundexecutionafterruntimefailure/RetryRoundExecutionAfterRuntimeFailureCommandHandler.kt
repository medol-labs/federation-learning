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
        require(state.currentState == RoundExecutionStateEnum.FAILED) {
            "RetryRoundExecutionAfterRuntimeFailure requires RoundExecution to be Failed."
        }
        val input = RetryRoundExecutionAfterRuntimeFailureInput(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, runtimeEngineJobId = command.runtimeEngineJobId, retryReason = command.retryReason)
        val portResult = retryRoundExecutionAfterRuntimeFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
