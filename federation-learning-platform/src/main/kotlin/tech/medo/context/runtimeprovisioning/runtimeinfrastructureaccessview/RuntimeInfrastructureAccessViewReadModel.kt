package tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;

import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeInfrastructureAccessViewReadModelQuery

class RuntimeInfrastructureAccessViewReadModelCriteria {
    var runtimeInfrastructureId: StringFilter? = null
    var organizationId: StringFilter? = null
    var runtimeInstallationPlanId: StringFilter? = null
    var runtimeInfrastructurePackageId: StringFilter? = null
    var runtimeInfrastructurePackageName: StringFilter? = null
    var runtimeInfrastructurePackageVersion: StringFilter? = null
    var organizationName: StringFilter? = null
    var runtimeName: StringFilter? = null
    var runtimeEnvironmentType: StringFilter? = null
    var agentInstallMode: StringFilter? = null
    var expectedNodeCount: IntegerFilter? = null
    var runtimeAgentId: StringFilter? = null
    var runtimeAgentVersion: StringFilter? = null
    var infrastructureVerifiedAt: RangeFilter<LocalDateTime>? = null
    var infrastructureVerificationFailedAt: RangeFilter<LocalDateTime>? = null
    var infrastructureVerificationFailureReason: StringFilter? = null
    var agentReadyAt: RangeFilter<LocalDateTime>? = null
    var agentDeploymentFailedAt: RangeFilter<LocalDateTime>? = null
    var agentDeploymentFailureReason: StringFilter? = null
    var agentDeploymentRetryFailedAt: RangeFilter<LocalDateTime>? = null
    var agentDeploymentRetryFailureReason: StringFilter? = null
    var connectedAt: RangeFilter<LocalDateTime>? = null
    var state: Filter<RuntimeInfrastructureStateEnum>? = null
}


class RuntimeInfrastructureAccessViewReadModelProjection : MetadataProjection {
    var runtimeInfrastructureId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInstallationPlanId: UUID? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var runtimeInfrastructurePackageName: String? = null
    var runtimeInfrastructurePackageVersion: String? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var runtimeEnvironmentType: String? = null
    var agentInstallMode: String? = null
    var expectedNodeCount: Int? = null
    var runtimeAgentId: UUID? = null
    var runtimeAgentVersion: String? = null
    var infrastructureVerifiedAt: LocalDateTime? = null
    var infrastructureVerificationFailedAt: LocalDateTime? = null
    var infrastructureVerificationFailureReason: String? = null
    var agentReadyAt: LocalDateTime? = null
    var agentDeploymentFailedAt: LocalDateTime? = null
    var agentDeploymentFailureReason: String? = null
    var agentDeploymentRetryFailedAt: LocalDateTime? = null
    var agentDeploymentRetryFailureReason: String? = null
    var connectedAt: LocalDateTime? = null
    var state: RuntimeInfrastructureStateEnum? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeInfrastructureAccessViewReadModelProjection.toReadModel(): RuntimeInfrastructureAccessViewReadModel =
    RuntimeInfrastructureAccessViewReadModel(
    runtimeInfrastructureId = runtimeInfrastructureId,
    organizationId = organizationId,
    runtimeInstallationPlanId = runtimeInstallationPlanId,
    runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
    runtimeInfrastructurePackageName = runtimeInfrastructurePackageName,
    runtimeInfrastructurePackageVersion = runtimeInfrastructurePackageVersion,
    organizationName = organizationName,
    runtimeName = runtimeName,
    runtimeEnvironmentType = runtimeEnvironmentType,
    agentInstallMode = agentInstallMode,
    expectedNodeCount = expectedNodeCount,
    runtimeAgentId = runtimeAgentId,
    runtimeAgentVersion = runtimeAgentVersion,
    infrastructureVerifiedAt = infrastructureVerifiedAt,
    infrastructureVerificationFailedAt = infrastructureVerificationFailedAt,
    infrastructureVerificationFailureReason = infrastructureVerificationFailureReason,
    agentReadyAt = agentReadyAt,
    agentDeploymentFailedAt = agentDeploymentFailedAt,
    agentDeploymentFailureReason = agentDeploymentFailureReason,
    agentDeploymentRetryFailedAt = agentDeploymentRetryFailedAt,
    agentDeploymentRetryFailureReason = agentDeploymentRetryFailureReason,
    connectedAt = connectedAt,
    state = state,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeInfrastructureAccessViewReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeInfrastructureAccessViewReadModel>
    fun findAllByCriteria(criteria: RuntimeInfrastructureAccessViewReadModelCriteria?, pageable: Pageable): Page<RuntimeInfrastructureAccessViewReadModel>
    fun findById(id: UUID): RuntimeInfrastructureAccessViewReadModel?
    fun findProjectionById(id: UUID): RuntimeInfrastructureAccessViewReadModelProjection?
    fun save(projection: RuntimeInfrastructureAccessViewReadModelProjection)
}

data class RuntimeInfrastructureAccessViewReadModel(
    val runtimeInfrastructureId: UUID?,
    val organizationId: UUID?,
    val runtimeInstallationPlanId: UUID?,
    val runtimeInfrastructurePackageId: UUID?,
    val runtimeInfrastructurePackageName: String?,
    val runtimeInfrastructurePackageVersion: String?,
    val organizationName: String?,
    val runtimeName: String?,
    val runtimeEnvironmentType: String?,
    val agentInstallMode: String?,
    val expectedNodeCount: Int?,
    val runtimeAgentId: UUID?,
    val runtimeAgentVersion: String?,
    val infrastructureVerifiedAt: LocalDateTime?,
    val infrastructureVerificationFailedAt: LocalDateTime?,
    val infrastructureVerificationFailureReason: String?,
    val agentReadyAt: LocalDateTime?,
    val agentDeploymentFailedAt: LocalDateTime?,
    val agentDeploymentFailureReason: String?,
    val agentDeploymentRetryFailedAt: LocalDateTime?,
    val agentDeploymentRetryFailureReason: String?,
    val connectedAt: LocalDateTime?,
    val state: RuntimeInfrastructureStateEnum?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
