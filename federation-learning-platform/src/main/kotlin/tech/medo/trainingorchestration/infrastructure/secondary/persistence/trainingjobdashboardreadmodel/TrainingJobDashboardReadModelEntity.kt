package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingjobdashboardreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum;
import java.math.BigDecimal;


@Entity
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
    var secureAggregationRequired: Boolean? = null
    @Enumerated(EnumType.STRING)
    var state: TrainingJobStateEnum? = null
    var workflowStage: String? = null
    var workflowStep: Int? = null
    var nextAction: String? = null
    @Column(columnDefinition = "text")
    var availableActions: String? = null
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
    var finalModelVersionId: UUID? = null
    var stopReason: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
