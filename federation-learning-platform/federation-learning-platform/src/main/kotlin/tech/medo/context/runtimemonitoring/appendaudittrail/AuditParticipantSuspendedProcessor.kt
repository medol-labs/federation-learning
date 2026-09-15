package tech.medo.runtimemonitoring.appendaudittrail

import tech.medo.federationmanagement.events.ParticipantSuspendedEvent
import tech.medo.runtimemonitoring.appendaudittrail.AppendAuditTrailCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-monitoring-append-audit-trail")
@Component
class AuditParticipantSuspendedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ParticipantSuspendedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(AppendAuditTrailCommand(sourceEventName = "" /* TODO: provide sourceEventName */, sourceEntityId = null /* TODO: provide sourceEntityId */, severity = "" /* TODO: provide severity */, payloadHash = "" /* TODO: provide payloadHash */)).resultMessage
}
