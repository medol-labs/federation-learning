package tech.medo.runtimeagentoperations.completeroundexecution

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.completeroundexecution.CompleteRoundExecutionCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState




@Component
class CompleteRoundExecutionCommandHandler(
    private val decision: CompleteRoundExecutionDecision
) {
    @CommandHandler
    fun handle(
        command: CompleteRoundExecutionCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
