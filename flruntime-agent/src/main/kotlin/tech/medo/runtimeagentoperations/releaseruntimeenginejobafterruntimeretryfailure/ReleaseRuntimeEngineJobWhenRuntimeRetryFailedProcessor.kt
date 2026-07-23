package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure

import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryFailedEvent
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReleaseRuntimeEngineJobWhenRuntimeRetryFailedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RoundExecutionRuntimeRetryFailedEvent): java.util.concurrent.CompletableFuture<ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand> =
        commandGateway.send(ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand(roundExecutionId = event.roundExecutionId, runtimeEngineJobId = event.runtimeEngineJobId, executionPlanId = event.executionPlanId)).resultMessage.thenApply { it.payload() as ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand }
}
