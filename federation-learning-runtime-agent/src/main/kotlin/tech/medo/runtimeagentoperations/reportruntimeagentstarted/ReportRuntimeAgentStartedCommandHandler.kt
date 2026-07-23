package tech.medo.runtimeagentoperations.reportruntimeagentstarted

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeagentstarted.ReportRuntimeAgentStartedCommand




@Component
class ReportRuntimeAgentStartedCommandHandler(
    private val decision: ReportRuntimeAgentStartedDecision
) {
    @CommandHandler
    fun handle(
        command: ReportRuntimeAgentStartedCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
