package tech.medo.runtimemonitoring.appendaudittrail

import tech.medo.federationmanagement.events.FederationSuspendedEvent
import tech.medo.runtimemonitoring.appendaudittrail.AppendAuditTrailCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class AuditFederationSuspendedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: FederationSuspendedEvent): java.util.concurrent.CompletableFuture<AppendAuditTrailCommand> =
        commandGateway.send(AppendAuditTrailCommand(auditRecordId = java.util.UUID.randomUUID() /* TODO: provide auditRecordId */, sourceEventName = "" /* TODO: provide sourceEventName */, sourceEntityId = null /* TODO: provide sourceEntityId */, severity = "" /* TODO: provide severity */, payloadHash = "" /* TODO: provide payloadHash */)).resultMessage.thenApply { it.payload() as AppendAuditTrailCommand }
}
