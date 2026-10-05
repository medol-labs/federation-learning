package tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry.ReportAgentRuntimeNodeResourceTelemetryCommand







@Component
class ReportAgentRuntimeNodeResourceTelemetryCommandHandler(
    private val decision: ReportAgentRuntimeNodeResourceTelemetryDecision
) {
    @CommandHandler
    fun handle(
        command: ReportAgentRuntimeNodeResourceTelemetryCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
