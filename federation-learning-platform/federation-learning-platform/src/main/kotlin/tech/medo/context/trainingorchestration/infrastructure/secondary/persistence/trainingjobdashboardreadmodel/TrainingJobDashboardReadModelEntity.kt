package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingjobdashboardreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "training_job_dashboard")
class TrainingJobDashboardReadModelEntity : MetadataProjection {
    @Id
    var trainingJobId: UUID? = null
    var federationId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    var federationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var objective: String? = null
    var strategyName: String? = null
    var aggregationAlgorithm: String? = null
    var personalizationMode: String? = null
    var personalizationScope: String? = null
    var personalizedArtifactPolicy: String? = null
    var secureAggregationRequired: Boolean? = null
    @Enumerated(EnumType.STRING)
    var state: TrainingJobStateEnum? = null
    var workflowStage: String? = null
    var workflowStep: Int? = null
    var nextAction: String? = null
    @Column(columnDefinition = "text")
    var availableActions: String? = null
    @Column(columnDefinition = "text")
    var blockedReason: String? = null
    var canSubmit: Boolean? = null
    var canStartRound: Boolean? = null
    var canPause: Boolean? = null
    var canResume: Boolean? = null
    var canCancel: Boolean? = null
    var canComplete: Boolean? = null
    var currentRoundNumber: Int? = null
    var startedRuntimeCount: Int? = null
    var minimumNodesPerRound: Int? = null
    var maxRounds: Int? = null
    var roundProgressPercent: Int? = null
    var globalAccuracy: BigDecimal? = null
    var finalModelId: UUID? = null
    var personalizedModelCount: Int? = null
    var meanPersonalizedAccuracy: BigDecimal? = null
    var worstPersonalizedAccuracy: BigDecimal? = null
    var meanPersonalizationLift: BigDecimal? = null
    @Column(columnDefinition = "text")
    var stopReason: String? = null
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
