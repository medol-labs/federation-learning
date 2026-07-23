package tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class RetryRoundExecutionAfterRuntimeFailureCommandHandler(
    private val decision: RetryRoundExecutionAfterRuntimeFailureDecision
) {
    @CommandHandler
    fun handle(
        command: RetryRoundExecutionAfterRuntimeFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
