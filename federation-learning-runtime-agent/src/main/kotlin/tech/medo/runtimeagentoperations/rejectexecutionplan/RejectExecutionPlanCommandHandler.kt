package tech.medo.runtimeagentoperations.rejectexecutionplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class RejectExecutionPlanCommandHandler(
    private val decision: RejectExecutionPlanDecision
) {
    @CommandHandler
    fun handle(
        command: RejectExecutionPlanCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
