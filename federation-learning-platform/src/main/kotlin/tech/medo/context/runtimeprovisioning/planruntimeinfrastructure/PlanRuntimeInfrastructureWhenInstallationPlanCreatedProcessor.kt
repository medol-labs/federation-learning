package tech.medo.runtimeprovisioning.planruntimeinfrastructure

import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.planruntimeinfrastructure.PlanRuntimeInfrastructureCommand
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.springframework.stereotype.Component

@Component
class PlanRuntimeInfrastructureWhenInstallationPlanCreatedProcessor(private val commandGateway: CommandGateway) {
    @DisallowReplay
    @EventHandler
    fun on(event: RuntimeInstallationPlanCreatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(PlanRuntimeInfrastructureCommand(runtimeInfrastructureId = event.runtimeInfrastructureId, runtimeInstallationPlanId = event.runtimeInstallationPlanId)).resultMessage
}
