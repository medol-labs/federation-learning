package tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum;

import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.StringFilter


class RuntimeInfrastructurePackageCatalogReadModelQuery

class RuntimeInfrastructurePackageCatalogReadModelCriteria {
    var runtimeInfrastructurePackageId: StringFilter? = null
    var packageName: StringFilter? = null
    var packageVersion: StringFilter? = null
    var runtimeEnvironmentType: StringFilter? = null
    var runtimeDeploymentTargetType: StringFilter? = null
    var installProfile: StringFilter? = null
    var architecture: StringFilter? = null
    var installGuide: StringFilter? = null
    var state: Filter<RuntimeInfrastructurePackageStateEnum>? = null
}


class RuntimeInfrastructurePackageCatalogReadModelProjection : MetadataProjection {
    var runtimeInfrastructurePackageId: UUID? = null
    var packageName: String? = null
    var packageVersion: String? = null
    var runtimeEnvironmentType: String? = null
    var runtimeDeploymentTargetType: String? = null
    var installProfile: String? = null
    var architecture: String? = null
    var installGuide: String? = null
    var state: RuntimeInfrastructurePackageStateEnum? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeInfrastructurePackageCatalogReadModelProjection.toReadModel(): RuntimeInfrastructurePackageCatalogReadModel =
    RuntimeInfrastructurePackageCatalogReadModel(
    runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
    packageName = packageName,
    packageVersion = packageVersion,
    runtimeEnvironmentType = runtimeEnvironmentType,
    runtimeDeploymentTargetType = runtimeDeploymentTargetType,
    installProfile = installProfile,
    architecture = architecture,
    installGuide = installGuide,
    state = state,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeInfrastructurePackageCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeInfrastructurePackageCatalogReadModel>
    fun findAllByCriteria(criteria: RuntimeInfrastructurePackageCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeInfrastructurePackageCatalogReadModel>
    fun findById(id: UUID): RuntimeInfrastructurePackageCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeInfrastructurePackageCatalogReadModelProjection?
    fun save(projection: RuntimeInfrastructurePackageCatalogReadModelProjection)
}

data class RuntimeInfrastructurePackageCatalogReadModel(
    val runtimeInfrastructurePackageId: UUID?,
    val packageName: String?,
    val packageVersion: String?,
    val runtimeEnvironmentType: String?,
    val runtimeDeploymentTargetType: String?,
    val installProfile: String?,
    val architecture: String?,
    val installGuide: String?,
    val state: RuntimeInfrastructurePackageStateEnum?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
