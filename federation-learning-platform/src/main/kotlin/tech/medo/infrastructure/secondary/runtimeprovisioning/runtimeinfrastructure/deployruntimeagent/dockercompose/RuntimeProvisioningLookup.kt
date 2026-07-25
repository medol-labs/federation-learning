package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelRepository
import java.util.UUID

@Component
class RuntimeProvisioningLookup(
    private val plans: RuntimeInstallationPlanCatalogReadModelRepository,
    private val packages: RuntimeInfrastructurePackageCatalogReadModelRepository
) {
    fun findPlanByRuntimeInfrastructureId(runtimeInfrastructureId: UUID): RuntimeInstallationPlanCatalogReadModel? =
        plans.findAll(Pageable.unpaged())
            .content
            .firstOrNull { it.runtimeInfrastructureId == runtimeInfrastructureId }

    fun findPackage(plan: RuntimeInstallationPlanCatalogReadModel): RuntimeInfrastructurePackageCatalogReadModel? =
        plan.runtimeInfrastructurePackageId?.let { packages.findById(it) }

    fun isDockerComposeRuntimeInfrastructure(
        runtimeInfrastructureId: UUID,
        properties: DockerComposeRuntimeInfrastructureProperties
    ): Boolean? {
        val plan = findPlanByRuntimeInfrastructureId(runtimeInfrastructureId) ?: return null
        val runtimePackage = findPackage(plan) ?: return null
        return properties.supportedDeploymentTargetTypes.any {
            it.equals(runtimePackage.runtimeDeploymentTargetType, ignoreCase = true)
        } || properties.supportedEnvironmentTypes.any {
            it.equals(runtimePackage.runtimeEnvironmentType, ignoreCase = true)
        }
    }
}
