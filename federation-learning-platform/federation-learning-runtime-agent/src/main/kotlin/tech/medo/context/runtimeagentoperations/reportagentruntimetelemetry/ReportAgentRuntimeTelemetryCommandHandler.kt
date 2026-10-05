package tech.medo.runtimeagentoperations.reportagentruntimetelemetry

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportagentruntimetelemetry.ReportAgentRuntimeTelemetryCommand







@Component
class ReportAgentRuntimeTelemetryCommandHandler(
    private val decision: ReportAgentRuntimeTelemetryDecision
) {
    @CommandHandler
    fun handle(
        command: ReportAgentRuntimeTelemetryCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
