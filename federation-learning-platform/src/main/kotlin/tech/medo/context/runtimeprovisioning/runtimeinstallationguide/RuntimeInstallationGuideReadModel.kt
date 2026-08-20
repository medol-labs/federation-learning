package tech.medo.runtimeprovisioning.runtimeinstallationguide

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;

import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeInstallationGuideReadModelQuery

class RuntimeInstallationGuideReadModelCriteria {
    var runtimeInstallationPlanId: StringFilter? = null
    var organizationId: StringFilter? = null
    var runtimeInfrastructureId: StringFilter? = null
    var runtimeInfrastructurePackageId: StringFilter? = null
    var runtimeInfrastructurePackageName: StringFilter? = null
    var runtimeInfrastructurePackageVersion: StringFilter? = null
    var infrastructureInstallGuide: StringFilter? = null
    var organizationName: StringFilter? = null
    var runtimeName: StringFilter? = null
    var bootstrapCommand: StringFilter? = null
    var runtimeDeploymentTargetType: StringFilter? = null
    var runtimeEnvironmentType: StringFilter? = null
    var agentInstallMode: StringFilter? = null
    var installProfile: StringFilter? = null
    var architecture: StringFilter? = null
    var expectedNodeCount: IntegerFilter? = null
}


class RuntimeInstallationGuideReadModelProjection : MetadataProjection {
    var runtimeInstallationPlanId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var runtimeInfrastructurePackageName: String? = null
    var runtimeInfrastructurePackageVersion: String? = null
    var infrastructureInstallGuide: String? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var bootstrapCommand: String? = null
    var runtimeDeploymentTargetType: String? = null
    var runtimeEnvironmentType: String? = null
    var agentInstallMode: String? = null
    var installProfile: String? = null
    var architecture: String? = null
    var expectedNodeCount: Int? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeInstallationGuideReadModelProjection.toReadModel(): RuntimeInstallationGuideReadModel =
    RuntimeInstallationGuideReadModel(
    runtimeInstallationPlanId = runtimeInstallationPlanId,
    organizationId = organizationId,
    runtimeInfrastructureId = runtimeInfrastructureId,
    runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
    runtimeInfrastructurePackageName = runtimeInfrastructurePackageName,
    runtimeInfrastructurePackageVersion = runtimeInfrastructurePackageVersion,
    infrastructureInstallGuide = infrastructureInstallGuide,
    organizationName = organizationName,
    runtimeName = runtimeName,
    bootstrapCommand = bootstrapCommand,
    runtimeDeploymentTargetType = runtimeDeploymentTargetType,
    runtimeEnvironmentType = runtimeEnvironmentType,
    agentInstallMode = agentInstallMode,
    installProfile = installProfile,
    architecture = architecture,
    expectedNodeCount = expectedNodeCount,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeInstallationGuideReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeInstallationGuideReadModel>
    fun findAllByCriteria(criteria: RuntimeInstallationGuideReadModelCriteria?, pageable: Pageable): Page<RuntimeInstallationGuideReadModel>
    fun findById(id: UUID): RuntimeInstallationGuideReadModel?
    fun findProjectionById(id: UUID): RuntimeInstallationGuideReadModelProjection?
    fun save(projection: RuntimeInstallationGuideReadModelProjection)
}

data class RuntimeInstallationGuideReadModel(
    val runtimeInstallationPlanId: UUID?,
    val organizationId: UUID?,
    val runtimeInfrastructureId: UUID?,
    val runtimeInfrastructurePackageId: UUID?,
    val runtimeInfrastructurePackageName: String?,
    val runtimeInfrastructurePackageVersion: String?,
    val infrastructureInstallGuide: String?,
    val organizationName: String?,
    val runtimeName: String?,
    val bootstrapCommand: String?,
    val runtimeDeploymentTargetType: String?,
    val runtimeEnvironmentType: String?,
    val agentInstallMode: String?,
    val installProfile: String?,
    val architecture: String?,
    val expectedNodeCount: Int?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
