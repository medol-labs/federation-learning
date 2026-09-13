package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeprovisioning.runtimeinstallationplan.RuntimeInstallationPlanSelection
import java.util.UUID;


@Command
data class CreateRuntimeInstallationPlanCommand(
    val runtimeInstallationPlanId: UUID = java.util.UUID.randomUUID(),
    val runtimeInfrastructureId: UUID = java.util.UUID.randomUUID(),
    val organizationId: UUID,
    val organizationName: String?,
    val runtimeInfrastructurePackageId: UUID,
    val runtimeInfrastructurePackageName: String?,
    val runtimeInfrastructurePackageVersion: String?,
    val runtimeEnvironmentType: String?,
    val runtimeName: String,
    val agentInstallMode: String,
    val expectedNodeCount: Int
) {
    @TargetEntityId
    val selection: RuntimeInstallationPlanSelection = RuntimeInstallationPlanSelection(organizationId = organizationId)

}
