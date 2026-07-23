package tech.medo.trainingorchestration.generateparticipantexecutionplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand




@Component
class GenerateParticipantExecutionPlanCommandHandler(
    private val decision: GenerateParticipantExecutionPlanDecision
) {
    @CommandHandler
    fun handle(
        command: GenerateParticipantExecutionPlanCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
