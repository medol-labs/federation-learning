package tech.medo.runtimeprovisioning.deployruntimeagent

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class DeployRuntimeAgentWhenInfrastructureVerifiedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeInfrastructureVerifiedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.agentInstallMode == "PLATFORM_MANAGED") {
            commandGateway.send(DeployRuntimeAgentCommand(runtimeAgentId = event.runtimeAgentId, runtimeInfrastructureId = event.runtimeInfrastructureId, runtimeInstallationPlanId = event.runtimeInstallationPlanId, organizationId = event.organizationId, organizationName = event.organizationName, runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = event.runtimeEnvironmentType, runtimeName = event.runtimeName, agentInstallMode = event.agentInstallMode, expectedNodeCount = event.expectedNodeCount)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
