package tech.medo.runtimeagentoperations.failroundexecution

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.failroundexecution.FailRoundExecutionCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState




@Component
class FailRoundExecutionCommandHandler(
    private val decision: FailRoundExecutionDecision
) {
    @CommandHandler
    fun handle(
        command: FailRoundExecutionCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
