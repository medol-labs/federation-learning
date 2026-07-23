package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class SubmitAgentLocalModelUpdateCommandHandler(
    private val decision: SubmitAgentLocalModelUpdateDecision
) {
    @CommandHandler
    fun handle(
        command: SubmitAgentLocalModelUpdateCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
