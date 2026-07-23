package tech.medo.trainingorchestration.dispatchparticipantexecutionplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanCommand

import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanState



@Component
class DispatchParticipantExecutionPlanCommandHandler(
    private val decision: DispatchParticipantExecutionPlanDecision
) {
    @CommandHandler
    fun handle(
        command: DispatchParticipantExecutionPlanCommand,
        @InjectEntity(idProperty = "executionPlanId") state: ParticipantExecutionPlanState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
