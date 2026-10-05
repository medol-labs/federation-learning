package tech.medo.runtimemonitoring.recordruntimetelemetry

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.recordruntimetelemetry.RecordRuntimeTelemetryCommand



import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthState



@Component
class RecordRuntimeTelemetryCommandHandler(
    private val decision: RecordRuntimeTelemetryDecision
) {
    @CommandHandler
    fun handle(
        command: RecordRuntimeTelemetryCommand,
        @InjectEntity(idProperty = "nodeId") state: NodeRuntimeHealthState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
