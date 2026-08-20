package tech.medo.runtimemonitoring.markruntimeagentrecovered

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.markruntimeagentrecovered.MarkRuntimeAgentRecoveredCommand

import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthState




@Component
class MarkRuntimeAgentRecoveredCommandHandler(
    private val decision: MarkRuntimeAgentRecoveredDecision
) {
    @CommandHandler
    fun handle(
        command: MarkRuntimeAgentRecoveredCommand,
        @InjectEntity(idProperty = "nodeId") state: NodeRuntimeHealthState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
