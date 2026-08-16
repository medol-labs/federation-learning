package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingroundprogressreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelKey
import java.util.UUID;
import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;


@IdClass(TrainingRoundProgressReadModelKey::class)
@Entity
class TrainingRoundProgressReadModelEntity : MetadataProjection {
    @Id
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    @Id
    var roundId: UUID? = null
    var trainingJobObjective: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var roundNumber: Int? = null
    @Enumerated(EnumType.STRING)
    var state: TrainingRoundStateEnum? = null
    @Column(columnDefinition = "text")
    var selectedOrganizationIds: String? = null
    @Column(columnDefinition = "text")
    var selectedParticipants: String? = null
    var selectedOrganizationCount: Int? = null
    var selectedRuntimeCount: Int? = null
    var targetRuntimeCount: Int? = null
    var executionPlanDispatchedCount: Int? = null
    var roundExecutionStartedCount: Int? = null
    var submittedModelUpdateCount: Int? = null
    var rejectedUpdateCount: Int? = null
    var acceptedModelUpdateCount: Int? = null
    var acceptedUpdateCount: Int? = null
    var pendingUpdateCount: Int? = null
    var failedRoundExecutionCount: Int? = null
    var completedRoundExecutionCount: Int? = null
    var retriedRoundExecutionCount: Int? = null
    var failedRoundExecutionRetryCount: Int? = null
    var quorumMet: Boolean? = null
    var quorumStatus: String? = null
    var minimumNodesPerRound: Int? = null
    var aggregationReady: Boolean? = null
    var secureAggregationRequired: Boolean? = null
    var secureAggregationStatus: String? = null
    var evaluationComplete: Boolean? = null
    var progressPercent: Int? = null
    var currentPhase: String? = null
    var nextAction: String? = null
    @Column(columnDefinition = "text")
    var blockedReason: String? = null
    @Column(columnDefinition = "text")
    var delayedReason: String? = null
    var roundStartedAt: LocalDateTime? = null
    var contributionDeadlineAt: LocalDateTime? = null
    var aggregationStartedAt: LocalDateTime? = null
    var evaluationSubmittedAt: LocalDateTime? = null
    var completedAt: LocalDateTime? = null
    var failedAt: LocalDateTime? = null
    var baseModelId: UUID? = null
    @Column(columnDefinition = "text")
    var artifactRefs: String? = null
    @Column(columnDefinition = "text")
    var rejectedUpdateReasons: String? = null
    var aggregatedModelId: UUID? = null
    @Column(columnDefinition = "text")
    var aggregatedModelArtifactUri: String? = null
    @Column(columnDefinition = "text")
    var aggregatedModelRegistryRef: String? = null
    var modelFormat: String? = null
    var modelArtifactDigest: String? = null
    @Column(columnDefinition = "text")
    var aggregatedModelSignatureUri: String? = null
    var globalAccuracy: BigDecimal? = null
    var globalFairnessScore: BigDecimal? = null
    @Column(columnDefinition = "text")
    var failureReason: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
