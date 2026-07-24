package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.k3s

import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModel

fun isK3sPackage(
    runtimePackage: RuntimeInfrastructurePackageCatalogReadModel,
    properties: K3sRuntimeInfrastructureProperties
): Boolean =
    properties.supportedDeploymentTargetTypes.any {
        it.equals(runtimePackage.runtimeDeploymentTargetType, ignoreCase = true)
    } || properties.supportedEnvironmentTypes.any {
        it.equals(runtimePackage.runtimeEnvironmentType, ignoreCase = true)
    }

fun k3sCommandFailure(command: String, result: K3sCommandResult, properties: K3sRuntimeInfrastructureProperties): String =
    if (result.timedOut) {
        "$command timed out after ${properties.commandTimeout}."
    } else {
        "$command failed with exitCode=${result.exitCode}: ${result.output}"
    }
