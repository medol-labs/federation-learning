package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.roundexecutioncatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal

@Entity
@Table(name = "round_execution_catalog")
class RoundExecutionCatalogReadModelEntity : MetadataProjection {
    @Id
    var roundExecutionId: UUID? = null
    var executionSessionId: UUID? = null
    var executionPlanId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var roundId: UUID? = null
    var roundNumber: Int? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    @Enumerated(EnumType.STRING)
    var state: RoundExecutionStateEnum? = null
    var featureSchemaId: UUID? = null
    var baseModelId: UUID? = null
    var runtimeEngineProfileId: UUID? = null
    var runtimeEngineProfileName: String? = null
    var runtimeEnginePluginProfile: String? = null
    var runtimeEngineImage: String? = null
    var runtimeEngineImageDigest: String? = null
    var runtimeEngineJobId: String? = null
    var runtimeEngineObservedStatus: String? = null
    var runtimeEngineObservationAt: LocalDateTime? = null
    var localUpdateArtifactRef: String? = null
    var metricsArtifactRef: String? = null
    var localExecutionRequirementsSatisfied: Boolean? = null
    var runtimeIdentityMatched: Boolean? = null
    var runtimeDatasetBindingAvailable: Boolean? = null
    var datasetAccessValidated: Boolean? = null
    var baseModelAvailable: Boolean? = null
    var trainingConfigurationSupported: Boolean? = null
    var runtimeResourceAvailable: Boolean? = null
    var runtimeAgentIdle: Boolean? = null
    var updateArtifactId: UUID? = null
    var artifactRef: String? = null
    var artifactDigest: String? = null
    var trainingLoss: BigDecimal? = null
    var receivedAt: LocalDateTime? = null
    var acceptedAt: LocalDateTime? = null
    var rejectedAt: LocalDateTime? = null
    var startedAt: LocalDateTime? = null
    var completedAt: LocalDateTime? = null
    var failedAt: LocalDateTime? = null
    var submittedAt: LocalDateTime? = null
    @Column(columnDefinition = "text")
    var failureReason: String? = null
    @Column(columnDefinition = "text")
    var retryReason: String? = null
    var runtimeEngineReleased: Boolean? = null
    @Column(columnDefinition = "text")
    var runtimeEngineReleaseFailureReason: String? = null
    @Column(columnDefinition = "text")
    var rejectionReasons: String? = null
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
