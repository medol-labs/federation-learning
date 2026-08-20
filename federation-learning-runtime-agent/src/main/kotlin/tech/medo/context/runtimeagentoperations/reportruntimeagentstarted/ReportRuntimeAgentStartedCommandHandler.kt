package tech.medo.runtimeagentoperations.reportruntimeagentstarted

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeagentstarted.ReportRuntimeAgentStartedCommand

import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState




@Component
class ReportRuntimeAgentStartedCommandHandler(
    private val decision: ReportRuntimeAgentStartedDecision
) {
    @CommandHandler
    fun handle(
        command: ReportRuntimeAgentStartedCommand,
        @InjectEntity(idProperty = "bootstrapRequestId") state: RuntimeAgentLifecycleState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
