package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

import tech.medo.runtimeagentoperations.events.AgentLocalModelUpdateSubmittedEvent
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReleaseRuntimeEngineJobWhenLocalModelUpdateSubmittedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: AgentLocalModelUpdateSubmittedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ReleaseRuntimeEngineJobAfterCompletionCommand(roundExecutionId = event.roundExecutionId, runtimeEngineJobId = event.runtimeEngineJobId, executionPlanId = event.executionPlanId)).resultMessage
}
