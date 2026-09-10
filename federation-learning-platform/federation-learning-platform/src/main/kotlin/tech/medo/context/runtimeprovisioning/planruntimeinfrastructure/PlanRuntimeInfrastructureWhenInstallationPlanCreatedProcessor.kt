package tech.medo.runtimeprovisioning.planruntimeinfrastructure

import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.planruntimeinfrastructure.PlanRuntimeInfrastructureCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class PlanRuntimeInfrastructureWhenInstallationPlanCreatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeInstallationPlanCreatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(PlanRuntimeInfrastructureCommand(runtimeInfrastructureId = event.runtimeInfrastructureId, runtimeInstallationPlanId = event.runtimeInstallationPlanId, organizationId = event.organizationId, organizationName = event.organizationName, runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = event.runtimeEnvironmentType, runtimeName = event.runtimeName, agentInstallMode = event.agentInstallMode, expectedNodeCount = event.expectedNodeCount)).resultMessage
}
