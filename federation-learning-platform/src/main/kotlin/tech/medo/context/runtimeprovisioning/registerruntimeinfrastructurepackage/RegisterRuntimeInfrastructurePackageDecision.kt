package tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage

import tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage.RegisterRuntimeInfrastructurePackageCommand

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePackageRegisteredEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackage.RuntimeInfrastructurePackageState





interface RegisterRuntimeInfrastructurePackageDecision {
    fun decide(command: RegisterRuntimeInfrastructurePackageCommand): List<Any> {
        return listOf(
            RuntimeInfrastructurePackageRegisteredEvent(runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, packageName = command.packageName, packageVersion = command.packageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeDeploymentTargetType = command.runtimeDeploymentTargetType, installProfile = command.installProfile, architecture = command.architecture, installGuide = command.installGuide, packageNameEventTag = "" /* TODO: derive value */)
        )
    }
}
