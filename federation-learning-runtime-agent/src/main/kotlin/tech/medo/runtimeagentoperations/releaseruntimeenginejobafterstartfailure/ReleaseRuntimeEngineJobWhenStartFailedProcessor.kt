package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import tech.medo.runtimeagentoperations.events.RoundExecutionStartFailedEvent
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReleaseRuntimeEngineJobWhenStartFailedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RoundExecutionStartFailedEvent): java.util.concurrent.CompletableFuture<ReleaseRuntimeEngineJobAfterStartFailureCommand> =
        commandGateway.send(ReleaseRuntimeEngineJobAfterStartFailureCommand(roundExecutionId = event.roundExecutionId, runtimeEngineJobId = event.runtimeEngineJobId, executionPlanId = event.executionPlanId)).resultMessage.thenApply { it.payload() as ReleaseRuntimeEngineJobAfterStartFailureCommand }
}
