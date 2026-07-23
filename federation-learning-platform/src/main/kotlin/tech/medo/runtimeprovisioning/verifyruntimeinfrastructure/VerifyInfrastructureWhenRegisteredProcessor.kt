package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class VerifyInfrastructureWhenRegisteredProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeInfrastructureRegisteredEvent): java.util.concurrent.CompletableFuture<VerifyRuntimeInfrastructureCommand> =
        commandGateway.send(VerifyRuntimeInfrastructureCommand(runtimeInfrastructureId = event.runtimeInfrastructureId, agentInstallMode = "" /* TODO: provide agentInstallMode */, verificationPassed = false /* TODO: provide verificationPassed */, observedNodeCount = 0 /* TODO: provide observedNodeCount */, failureReason = null /* TODO: provide failureReason */)).resultMessage.thenApply { it.payload() as VerifyRuntimeInfrastructureCommand }
}
