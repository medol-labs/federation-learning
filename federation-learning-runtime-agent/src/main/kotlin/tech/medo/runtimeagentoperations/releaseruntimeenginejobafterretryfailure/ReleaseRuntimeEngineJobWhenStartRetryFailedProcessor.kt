package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure

import tech.medo.runtimeagentoperations.events.RoundExecutionStartRetryFailedEvent
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReleaseRuntimeEngineJobWhenStartRetryFailedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RoundExecutionStartRetryFailedEvent): java.util.concurrent.CompletableFuture<ReleaseRuntimeEngineJobAfterRetryFailureCommand> =
        commandGateway.send(ReleaseRuntimeEngineJobAfterRetryFailureCommand(roundExecutionId = event.roundExecutionId, runtimeEngineJobId = event.runtimeEngineJobId, executionPlanId = event.executionPlanId)).resultMessage.thenApply { it.payload() as ReleaseRuntimeEngineJobAfterRetryFailureCommand }
}
