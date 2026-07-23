package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure

import tech.medo.runtimeagentoperations.events.RoundExecutionFailedEvent
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReleaseRuntimeEngineJobWhenRoundExecutionFailedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RoundExecutionFailedEvent): java.util.concurrent.CompletableFuture<ReleaseRuntimeEngineJobAfterFailureCommand> =
        commandGateway.send(ReleaseRuntimeEngineJobAfterFailureCommand(roundExecutionId = event.roundExecutionId, runtimeEngineJobId = event.runtimeEngineJobId, executionPlanId = event.executionPlanId)).resultMessage.thenApply { it.payload() as ReleaseRuntimeEngineJobAfterFailureCommand }
}
