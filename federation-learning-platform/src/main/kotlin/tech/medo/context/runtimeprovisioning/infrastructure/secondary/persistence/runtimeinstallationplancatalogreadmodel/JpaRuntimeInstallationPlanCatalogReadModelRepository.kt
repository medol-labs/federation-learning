package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationplancatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.toReadModel

@Repository
class JpaRuntimeInstallationPlanCatalogReadModelRepository(
    private val jpaRepository: SpringDataRuntimeInstallationPlanCatalogReadModelRepository,
    private val queryService: RuntimeInstallationPlanCatalogReadModelQueryService
) : RuntimeInstallationPlanCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeInstallationPlanCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeInstallationPlanCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeInstallationPlanCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RuntimeInstallationPlanCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeInstallationPlanCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeInstallationPlanCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeInstallationPlanCatalogReadModelEntity.toProjection(): RuntimeInstallationPlanCatalogReadModelProjection =
        RuntimeInstallationPlanCatalogReadModelProjection().also {
            it.runtimeInstallationPlanId = this@toProjection.runtimeInstallationPlanId
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toProjection.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toProjection.runtimeInfrastructurePackageVersion
            it.runtimeName = this@toProjection.runtimeName
            it.agentInstallMode = this@toProjection.agentInstallMode
            it.expectedNodeCount = this@toProjection.expectedNodeCount
            it.planStatus = this@toProjection.planStatus
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.observedNodeCount = this@toProjection.observedNodeCount
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeAgentVersion = this@toProjection.runtimeAgentVersion
            it.plannedAt = this@toProjection.plannedAt
            it.verifiedAt = this@toProjection.verifiedAt
            it.verificationFailedAt = this@toProjection.verificationFailedAt
            it.verificationFailureReason = this@toProjection.verificationFailureReason
            it.agentReadyAt = this@toProjection.agentReadyAt
            it.agentDeploymentFailedAt = this@toProjection.agentDeploymentFailedAt
            it.agentDeploymentFailureReason = this@toProjection.agentDeploymentFailureReason
            it.agentDeploymentRetryFailedAt = this@toProjection.agentDeploymentRetryFailedAt
            it.agentDeploymentRetryFailureReason = this@toProjection.agentDeploymentRetryFailureReason
            it.lastConnectedAt = this@toProjection.lastConnectedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeInstallationPlanCatalogReadModelProjection.toEntity(): RuntimeInstallationPlanCatalogReadModelEntity =
        RuntimeInstallationPlanCatalogReadModelEntity().also {
            it.runtimeInstallationPlanId = this@toEntity.runtimeInstallationPlanId
            it.organizationId = this@toEntity.organizationId
            it.organizationName = this@toEntity.organizationName
            it.runtimeInfrastructurePackageId = this@toEntity.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toEntity.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toEntity.runtimeInfrastructurePackageVersion
            it.runtimeName = this@toEntity.runtimeName
            it.agentInstallMode = this@toEntity.agentInstallMode
            it.expectedNodeCount = this@toEntity.expectedNodeCount
            it.planStatus = this@toEntity.planStatus
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.observedNodeCount = this@toEntity.observedNodeCount
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.runtimeAgentVersion = this@toEntity.runtimeAgentVersion
            it.plannedAt = this@toEntity.plannedAt
            it.verifiedAt = this@toEntity.verifiedAt
            it.verificationFailedAt = this@toEntity.verificationFailedAt
            it.verificationFailureReason = this@toEntity.verificationFailureReason
            it.agentReadyAt = this@toEntity.agentReadyAt
            it.agentDeploymentFailedAt = this@toEntity.agentDeploymentFailedAt
            it.agentDeploymentFailureReason = this@toEntity.agentDeploymentFailureReason
            it.agentDeploymentRetryFailedAt = this@toEntity.agentDeploymentRetryFailedAt
            it.agentDeploymentRetryFailureReason = this@toEntity.agentDeploymentRetryFailureReason
            it.lastConnectedAt = this@toEntity.lastConnectedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
