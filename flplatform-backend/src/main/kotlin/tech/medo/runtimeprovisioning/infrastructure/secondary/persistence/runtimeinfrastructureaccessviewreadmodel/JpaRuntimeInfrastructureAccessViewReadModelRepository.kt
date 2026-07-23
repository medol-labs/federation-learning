package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructureaccessviewreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModel
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.toReadModel

@Repository
class JpaRuntimeInfrastructureAccessViewReadModelRepository(private val jpaRepository: SpringDataRuntimeInfrastructureAccessViewReadModelRepository) : RuntimeInfrastructureAccessViewReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeInfrastructureAccessViewReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeInfrastructureAccessViewReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeInfrastructureAccessViewReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeInfrastructureAccessViewReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeInfrastructureAccessViewReadModelEntity.toProjection(): RuntimeInfrastructureAccessViewReadModelProjection =
        RuntimeInfrastructureAccessViewReadModelProjection().also {
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.organizationId = this@toProjection.organizationId
            it.runtimeInstallationPlanId = this@toProjection.runtimeInstallationPlanId
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toProjection.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toProjection.runtimeInfrastructurePackageVersion
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.runtimeDeploymentTargetType = this@toProjection.runtimeDeploymentTargetType
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.agentInstallMode = this@toProjection.agentInstallMode
            it.expectedNodeCount = this@toProjection.expectedNodeCount
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeAgentVersion = this@toProjection.runtimeAgentVersion
            it.infrastructureVerifiedAt = this@toProjection.infrastructureVerifiedAt
            it.infrastructureVerificationFailedAt = this@toProjection.infrastructureVerificationFailedAt
            it.infrastructureVerificationFailureReason = this@toProjection.infrastructureVerificationFailureReason
            it.agentReadyAt = this@toProjection.agentReadyAt
            it.agentDeploymentFailedAt = this@toProjection.agentDeploymentFailedAt
            it.agentDeploymentFailureReason = this@toProjection.agentDeploymentFailureReason
            it.agentDeploymentRetryFailedAt = this@toProjection.agentDeploymentRetryFailedAt
            it.agentDeploymentRetryFailureReason = this@toProjection.agentDeploymentRetryFailureReason
            it.connectedAt = this@toProjection.connectedAt
            it.state = this@toProjection.state
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeInfrastructureAccessViewReadModelProjection.toEntity(): RuntimeInfrastructureAccessViewReadModelEntity =
        RuntimeInfrastructureAccessViewReadModelEntity().also {
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.organizationId = this@toEntity.organizationId
            it.runtimeInstallationPlanId = this@toEntity.runtimeInstallationPlanId
            it.runtimeInfrastructurePackageId = this@toEntity.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toEntity.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toEntity.runtimeInfrastructurePackageVersion
            it.organizationName = this@toEntity.organizationName
            it.runtimeName = this@toEntity.runtimeName
            it.runtimeDeploymentTargetType = this@toEntity.runtimeDeploymentTargetType
            it.runtimeEnvironmentType = this@toEntity.runtimeEnvironmentType
            it.agentInstallMode = this@toEntity.agentInstallMode
            it.expectedNodeCount = this@toEntity.expectedNodeCount
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.runtimeAgentVersion = this@toEntity.runtimeAgentVersion
            it.infrastructureVerifiedAt = this@toEntity.infrastructureVerifiedAt
            it.infrastructureVerificationFailedAt = this@toEntity.infrastructureVerificationFailedAt
            it.infrastructureVerificationFailureReason = this@toEntity.infrastructureVerificationFailureReason
            it.agentReadyAt = this@toEntity.agentReadyAt
            it.agentDeploymentFailedAt = this@toEntity.agentDeploymentFailedAt
            it.agentDeploymentFailureReason = this@toEntity.agentDeploymentFailureReason
            it.agentDeploymentRetryFailedAt = this@toEntity.agentDeploymentRetryFailedAt
            it.agentDeploymentRetryFailureReason = this@toEntity.agentDeploymentRetryFailureReason
            it.connectedAt = this@toEntity.connectedAt
            it.state = this@toEntity.state
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
