package tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackage.RuntimeInfrastructurePackageSelection
import java.util.UUID;


@Command
data class RegisterRuntimeInfrastructurePackageCommand(
    val runtimeInfrastructurePackageId: UUID = java.util.UUID.randomUUID(),
    val packageName: String,
    val packageVersion: String,
    val runtimeEnvironmentType: String,
    val runtimeDeploymentTargetType: String,
    val installProfile: String,
    val architecture: String,
    val installGuide: String
) {
    @TargetEntityId
    val selection: RuntimeInfrastructurePackageSelection = RuntimeInfrastructurePackageSelection(packageName = packageName.trim().lowercase(), packageVersion = packageVersion)

}
