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
        require(state.currentState == RoundExecutionStateEnum.START_FAILED) {
            "RetryRoundExecutionAfterStartFailure requires RoundExecution to be StartFailed."
        }
        val input = RetryRoundExecutionAfterStartFailureInput(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, runtimeEngineJobId = command.runtimeEngineJobId, retryReason = command.retryReason)
        val portResult = retryRoundExecutionAfterStartFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
