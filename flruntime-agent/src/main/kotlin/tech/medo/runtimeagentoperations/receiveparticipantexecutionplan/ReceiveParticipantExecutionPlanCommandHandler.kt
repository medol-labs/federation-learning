package tech.medo.runtimeagentoperations.receiveparticipantexecutionplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.receiveparticipantexecutionplan.ReceiveParticipantExecutionPlanCommand




@Component
class ReceiveParticipantExecutionPlanCommandHandler(
    private val decision: ReceiveParticipantExecutionPlanDecision
) {
    @CommandHandler
    fun handle(
        command: ReceiveParticipantExecutionPlanCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
