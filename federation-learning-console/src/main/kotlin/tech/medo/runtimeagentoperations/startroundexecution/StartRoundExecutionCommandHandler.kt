package tech.medo.runtimeagentoperations.startroundexecution

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class StartRoundExecutionCommandHandler(
    private val decision: StartRoundExecutionDecision
) {
    @CommandHandler
    fun handle(
        command: StartRoundExecutionCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
