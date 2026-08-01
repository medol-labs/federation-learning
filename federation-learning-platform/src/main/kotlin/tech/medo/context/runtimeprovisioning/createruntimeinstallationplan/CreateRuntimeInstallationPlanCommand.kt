package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeprovisioning.runtimeinstallationplan.RuntimeInstallationPlanSelection
import java.util.UUID;


@Command
data class CreateRuntimeInstallationPlanCommand(
    val runtimeInstallationPlanId: UUID = java.util.UUID.randomUUID(),
    val organizationId: UUID,
    val runtimeInfrastructurePackageId: UUID,
    val runtimeName: String,
    val agentInstallMode: String,
    val expectedNodeCount: Int
) {
    @TargetEntityId
    val selection: RuntimeInstallationPlanSelection = RuntimeInstallationPlanSelection(organizationId = organizationId)

}
