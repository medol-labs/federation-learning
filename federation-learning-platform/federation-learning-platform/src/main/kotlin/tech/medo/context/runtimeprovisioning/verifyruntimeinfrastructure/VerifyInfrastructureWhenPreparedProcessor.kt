package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePreparedEvent
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class VerifyInfrastructureWhenPreparedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeInfrastructurePreparedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(VerifyRuntimeInfrastructureCommand(runtimeInfrastructureId = event.runtimeInfrastructureId, runtimeInstallationPlanId = event.runtimeInstallationPlanId, organizationId = event.organizationId, organizationName = event.organizationName, runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = event.runtimeEnvironmentType, runtimeName = event.runtimeName, expectedNodeCount = event.expectedNodeCount, runtimeAgentId = event.runtimeAgentId)).resultMessage
}
