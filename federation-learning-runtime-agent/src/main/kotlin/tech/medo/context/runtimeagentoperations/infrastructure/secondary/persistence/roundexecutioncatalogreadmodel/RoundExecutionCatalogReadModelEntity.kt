package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.roundexecutioncatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;


@Entity
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
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
