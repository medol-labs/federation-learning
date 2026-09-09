package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationguidereadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;

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
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeInfrastructureState = this@toProjection.runtimeInfrastructureState
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toProjection.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toProjection.runtimeInfrastructurePackageVersion
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.bootstrapCommand = this@toProjection.bootstrapCommand
            it.nodeLabelCommand = this@toProjection.nodeLabelCommand
            it.nodeTaintCommand = this@toProjection.nodeTaintCommand
            it.runtimeAgentNodeSelectorYaml = this@toProjection.runtimeAgentNodeSelectorYaml
            it.runtimeAgentTolerationsYaml = this@toProjection.runtimeAgentTolerationsYaml
            it.bootstrapConfigYaml = this@toProjection.bootstrapConfigYaml
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.agentInstallMode = this@toProjection.agentInstallMode
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
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.runtimeInfrastructureState = this@toEntity.runtimeInfrastructureState
            it.runtimeInfrastructurePackageId = this@toEntity.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toEntity.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toEntity.runtimeInfrastructurePackageVersion
            it.organizationName = this@toEntity.organizationName
            it.runtimeName = this@toEntity.runtimeName
            it.bootstrapCommand = this@toEntity.bootstrapCommand
            it.nodeLabelCommand = this@toEntity.nodeLabelCommand
            it.nodeTaintCommand = this@toEntity.nodeTaintCommand
            it.runtimeAgentNodeSelectorYaml = this@toEntity.runtimeAgentNodeSelectorYaml
            it.runtimeAgentTolerationsYaml = this@toEntity.runtimeAgentTolerationsYaml
            it.bootstrapConfigYaml = this@toEntity.bootstrapConfigYaml
            it.runtimeEnvironmentType = this@toEntity.runtimeEnvironmentType
            it.agentInstallMode = this@toEntity.agentInstallMode
            it.expectedNodeCount = this@toEntity.expectedNodeCount
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
