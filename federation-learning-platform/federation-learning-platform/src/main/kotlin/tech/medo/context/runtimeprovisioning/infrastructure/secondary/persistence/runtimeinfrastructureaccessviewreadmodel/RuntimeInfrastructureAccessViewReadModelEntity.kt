package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructureaccessviewreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum

@Entity
@Table(name = "runtime_infrastructure_access_view")
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
    var runtimeEnvironmentType: String? = null
    var agentInstallMode: String? = null
    var expectedNodeCount: Int? = null
    var runtimeAgentId: UUID? = null
    var runtimeAgentVersion: String? = null
    var infrastructurePreparedAt: LocalDateTime? = null
    var preparedNodeCount: Int? = null
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
    var projectionUpdatedAt: LocalDateTime? = null
    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    override fun assignMetadata(metadata: tech.medo.shared.application.metadata.MetadataSnapshot, updateTimestamp: Boolean) {
        if (updateTimestamp) this.projectionUpdatedAt = metadata.projectionUpdatedAt
        this.userId = metadata.userId
        this.sessionId = metadata.sessionId
        this.correlationId = metadata.correlationId
        this.causationId = metadata.causationId
        this.traceId = metadata.traceId
        this.tenantId = metadata.tenantId
    }
}
