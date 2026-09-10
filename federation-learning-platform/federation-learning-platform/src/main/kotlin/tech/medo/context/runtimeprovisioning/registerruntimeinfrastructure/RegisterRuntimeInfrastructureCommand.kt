package tech.medo.runtimeprovisioning.registerruntimeinfrastructure

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureSelection
import java.util.UUID;


@Command
data class RegisterRuntimeInfrastructureCommand(
    val runtimeInfrastructureId: UUID,
    val runtimeInstallationPlanId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val runtimeInfrastructurePackageId: UUID,
    val runtimeInfrastructurePackageName: String?,
    val runtimeInfrastructurePackageVersion: String?,
    val runtimeEnvironmentType: String?,
    val runtimeName: String,
    val agentInstallMode: String,
    val expectedNodeCount: Int,
    val runtimeAgentId: UUID = java.util.UUID.randomUUID()
) {
    @TargetEntityId
    val selection: RuntimeInfrastructureSelection = RuntimeInfrastructureSelection(runtimeInfrastructureId = runtimeInfrastructureId)

}
