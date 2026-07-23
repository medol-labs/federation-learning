package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

import tech.medo.runtimeagentoperations.events.RoundExecutionCompletedEvent
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReleaseRuntimeEngineJobWhenRoundExecutionCompletedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RoundExecutionCompletedEvent): java.util.concurrent.CompletableFuture<ReleaseRuntimeEngineJobAfterCompletionCommand> =
        commandGateway.send(ReleaseRuntimeEngineJobAfterCompletionCommand(roundExecutionId = event.roundExecutionId, runtimeEngineJobId = event.runtimeEngineJobId, executionPlanId = event.executionPlanId)).resultMessage.thenApply { it.payload() as ReleaseRuntimeEngineJobAfterCompletionCommand }
}
