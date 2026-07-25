package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructureaccessviewreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;


@Entity
class RuntimeInfrastructureAccessViewReadModelEntity : MetadataProjection {
    @Id
    var runtimeInfrastructureId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInstallationPlanId: UUID? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var runtimeInfrastructurePackageName: String? = null
    var runtimeInfrastructurePackageVersion: String? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var runtimeDeploymentTargetType: String? = null
    var runtimeEnvironmentType: String? = null
    var agentInstallMode: String? = null
    var expectedNodeCount: Int? = null
    var runtimeAgentId: UUID? = null
    var runtimeAgentVersion: String? = null
    var infrastructureVerifiedAt: LocalDateTime? = null
    var infrastructureVerificationFailedAt: LocalDateTime? = null
    @Column(columnDefinition = "text")
    var infrastructureVerificationFailureReason: String? = null
    var agentReadyAt: LocalDateTime? = null
    var agentDeploymentFailedAt: LocalDateTime? = null
    @Column(columnDefinition = "text")
    var agentDeploymentFailureReason: String? = null
    var agentDeploymentRetryFailedAt: LocalDateTime? = null
    @Column(columnDefinition = "text")
    var agentDeploymentRetryFailureReason: String? = null
    var connectedAt: LocalDateTime? = null
    @Enumerated(EnumType.STRING)
    var state: RuntimeInfrastructureStateEnum? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
