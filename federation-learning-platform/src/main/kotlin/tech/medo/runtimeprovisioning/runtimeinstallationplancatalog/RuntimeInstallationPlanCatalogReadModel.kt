package tech.medo.runtimeprovisioning.runtimeinstallationplancatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class RuntimeInstallationPlanCatalogReadModelQuery

class RuntimeInstallationPlanCatalogReadModelProjection : MetadataProjection {
    var runtimeInstallationPlanId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var runtimeInfrastructurePackageName: String? = null
    var runtimeInfrastructurePackageVersion: String? = null
    var runtimeName: String? = null
    var agentInstallMode: String? = null
    var expectedNodeCount: Int? = null
    var planStatus: String? = null
    var runtimeInfrastructureId: UUID? = null
    var observedNodeCount: Int? = null
    var runtimeAgentId: UUID? = null
    var runtimeAgentVersion: String? = null
    var plannedAt: LocalDateTime? = null
    var verifiedAt: LocalDateTime? = null
    var verificationFailedAt: LocalDateTime? = null
    var verificationFailureReason: String? = null
    var agentReadyAt: LocalDateTime? = null
    var agentDeploymentFailedAt: LocalDateTime? = null
    var agentDeploymentFailureReason: String? = null
    var agentDeploymentRetryFailedAt: LocalDateTime? = null
    var agentDeploymentRetryFailureReason: String? = null
    var lastConnectedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeInstallationPlanCatalogReadModelProjection.toReadModel(): RuntimeInstallationPlanCatalogReadModel =
    RuntimeInstallationPlanCatalogReadModel(
    runtimeInstallationPlanId = runtimeInstallationPlanId,
    organizationId = organizationId,
    organizationName = organizationName,
    runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
    runtimeInfrastructurePackageName = runtimeInfrastructurePackageName,
    runtimeInfrastructurePackageVersion = runtimeInfrastructurePackageVersion,
    runtimeName = runtimeName,
    agentInstallMode = agentInstallMode,
    expectedNodeCount = expectedNodeCount,
    planStatus = planStatus,
    runtimeInfrastructureId = runtimeInfrastructureId,
    observedNodeCount = observedNodeCount,
    runtimeAgentId = runtimeAgentId,
    runtimeAgentVersion = runtimeAgentVersion,
    plannedAt = plannedAt,
    verifiedAt = verifiedAt,
    verificationFailedAt = verificationFailedAt,
    verificationFailureReason = verificationFailureReason,
    agentReadyAt = agentReadyAt,
    agentDeploymentFailedAt = agentDeploymentFailedAt,
    agentDeploymentFailureReason = agentDeploymentFailureReason,
    agentDeploymentRetryFailedAt = agentDeploymentRetryFailedAt,
    agentDeploymentRetryFailureReason = agentDeploymentRetryFailureReason,
    lastConnectedAt = lastConnectedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeInstallationPlanCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeInstallationPlanCatalogReadModel>
    fun findById(id: UUID): RuntimeInstallationPlanCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeInstallationPlanCatalogReadModelProjection?
    fun save(projection: RuntimeInstallationPlanCatalogReadModelProjection)
}

data class RuntimeInstallationPlanCatalogReadModel(
    val runtimeInstallationPlanId: UUID?,
    val organizationId: UUID?,
    val organizationName: String?,
    val runtimeInfrastructurePackageId: UUID?,
    val runtimeInfrastructurePackageName: String?,
    val runtimeInfrastructurePackageVersion: String?,
    val runtimeName: String?,
    val agentInstallMode: String?,
    val expectedNodeCount: Int?,
    val planStatus: String?,
    val runtimeInfrastructureId: UUID?,
    val observedNodeCount: Int?,
    val runtimeAgentId: UUID?,
    val runtimeAgentVersion: String?,
    val plannedAt: LocalDateTime?,
    val verifiedAt: LocalDateTime?,
    val verificationFailedAt: LocalDateTime?,
    val verificationFailureReason: String?,
    val agentReadyAt: LocalDateTime?,
    val agentDeploymentFailedAt: LocalDateTime?,
    val agentDeploymentFailureReason: String?,
    val agentDeploymentRetryFailedAt: LocalDateTime?,
    val agentDeploymentRetryFailureReason: String?,
    val lastConnectedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
