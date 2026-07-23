package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationplancatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class RuntimeInstallationPlanCatalogReadModelEntity : MetadataProjection {
    @Id
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
