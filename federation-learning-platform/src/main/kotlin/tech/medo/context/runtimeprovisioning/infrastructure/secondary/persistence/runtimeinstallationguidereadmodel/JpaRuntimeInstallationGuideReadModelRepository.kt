package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationguidereadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.runtimeprovisioning.runtimeinstallationguide.RuntimeInstallationGuideReadModel
import tech.medo.runtimeprovisioning.runtimeinstallationguide.RuntimeInstallationGuideReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinstallationguide.RuntimeInstallationGuideReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinstallationguide.RuntimeInstallationGuideReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinstallationguide.toReadModel

@Repository
class JpaRuntimeInstallationGuideReadModelRepository(
    private val jpaRepository: SpringDataRuntimeInstallationGuideReadModelRepository,
    private val queryService: RuntimeInstallationGuideReadModelQueryService
) : RuntimeInstallationGuideReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeInstallationGuideReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeInstallationGuideReadModelCriteria?, pageable: Pageable): Page<RuntimeInstallationGuideReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RuntimeInstallationGuideReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeInstallationGuideReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeInstallationGuideReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeInstallationGuideReadModelEntity.toProjection(): RuntimeInstallationGuideReadModelProjection =
        RuntimeInstallationGuideReadModelProjection().also {
            it.runtimeInstallationPlanId = this@toProjection.runtimeInstallationPlanId
            it.organizationId = this@toProjection.organizationId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toProjection.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toProjection.runtimeInfrastructurePackageVersion
            it.infrastructureInstallGuide = this@toProjection.infrastructureInstallGuide
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.bootstrapCommand = this@toProjection.bootstrapCommand
            it.runtimeDeploymentTargetType = this@toProjection.runtimeDeploymentTargetType
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.agentInstallMode = this@toProjection.agentInstallMode
            it.installProfile = this@toProjection.installProfile
            it.architecture = this@toProjection.architecture
            it.expectedNodeCount = this@toProjection.expectedNodeCount
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeInstallationGuideReadModelProjection.toEntity(): RuntimeInstallationGuideReadModelEntity =
        RuntimeInstallationGuideReadModelEntity().also {
            it.runtimeInstallationPlanId = this@toEntity.runtimeInstallationPlanId
            it.organizationId = this@toEntity.organizationId
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.runtimeInfrastructurePackageId = this@toEntity.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toEntity.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toEntity.runtimeInfrastructurePackageVersion
            it.infrastructureInstallGuide = this@toEntity.infrastructureInstallGuide
            it.organizationName = this@toEntity.organizationName
            it.runtimeName = this@toEntity.runtimeName
            it.bootstrapCommand = this@toEntity.bootstrapCommand
            it.runtimeDeploymentTargetType = this@toEntity.runtimeDeploymentTargetType
            it.runtimeEnvironmentType = this@toEntity.runtimeEnvironmentType
            it.agentInstallMode = this@toEntity.agentInstallMode
            it.installProfile = this@toEntity.installProfile
            it.architecture = this@toEntity.architecture
            it.expectedNodeCount = this@toEntity.expectedNodeCount
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
