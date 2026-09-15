package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import tech.medo.runtimeagentoperations.events.RoundExecutionStartFailedEvent
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-agent-operations-release-runtime-engine-job-after-start-failure")
@Component
class ReleaseRuntimeEngineJobWhenStartFailedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RoundExecutionStartFailedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ReleaseRuntimeEngineJobAfterStartFailureCommand(roundExecutionId = event.roundExecutionId, runtimeEngineJobId = event.runtimeEngineJobId, executionPlanId = event.executionPlanId)).resultMessage
}
