package tech.medo.runtimegovernance.detectruntimecapabilities

import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-governance-detect-runtime-capabilities")
@Component
class DetectRuntimeCapabilitiesWhenRuntimeActivatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeIdentityActivatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(DetectRuntimeCapabilitiesCommand(runtimeId = event.runtimeId, capabilityTypes = emptyList() /* TODO: provide capabilityTypes */)).resultMessage
}
