package tech.medo.runtimemonitoring.recordruntimenoderesourcetelemetry

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.recordruntimenoderesourcetelemetry.RecordRuntimeNodeResourceTelemetryCommand







@Component
class RecordRuntimeNodeResourceTelemetryCommandHandler(
    private val decision: RecordRuntimeNodeResourceTelemetryDecision
) {
    @CommandHandler
    fun handle(
        command: RecordRuntimeNodeResourceTelemetryCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
