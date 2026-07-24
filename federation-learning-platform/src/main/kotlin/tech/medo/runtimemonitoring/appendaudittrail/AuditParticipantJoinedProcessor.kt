package tech.medo.runtimemonitoring.appendaudittrail

import tech.medo.federationmanagement.events.ParticipantJoinedEvent
import tech.medo.runtimemonitoring.appendaudittrail.AppendAuditTrailCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class AuditParticipantJoinedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ParticipantJoinedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(AppendAuditTrailCommand(auditRecordId = java.util.UUID.randomUUID() /* TODO: provide auditRecordId */, sourceEventName = "" /* TODO: provide sourceEventName */, sourceEntityId = null /* TODO: provide sourceEntityId */, severity = "" /* TODO: provide severity */, payloadHash = "" /* TODO: provide payloadHash */)).resultMessage
}
