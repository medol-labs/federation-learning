package tech.medo.modellifecycle.infrastructure.secondary.persistence.modelcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.modellifecycle.domain.states.ModelStateEnum;


@Entity
class ModelCatalogReadModelEntity : MetadataProjection {
    @Id
    var modelId: UUID? = null
    var trainingJobId: UUID? = null
    var finalRoundId: UUID? = null
    var modelArtifactId: UUID? = null
    var trainingJobObjective: String? = null
    var modelArtifactDigest: String? = null
    var evaluationReportId: UUID? = null
    var finalGlobalAccuracy: BigDecimal? = null
    @Enumerated(EnumType.STRING)
    var state: ModelStateEnum? = null
    var releaseChannel: String? = null
    var productionStage: String? = null
    var previousModelId: UUID? = null
    var experimentId: UUID? = null
    var hyperparameterSnapshotId: UUID? = null
    var reproducibilityManifestId: UUID? = null
    var modelCardId: UUID? = null
    var baselineModelId: UUID? = null
    var hasEvaluationPackage: Boolean? = null
    var approvalStatus: String? = null
    var releaseStatus: String? = null
    var isProduction: Boolean? = null
    var canRecordEvaluationPackage: Boolean? = null
    var canApprove: Boolean? = null
    var canPromoteToProduction: Boolean? = null
    var canRollback: Boolean? = null
    var canRetire: Boolean? = null
    @Column(columnDefinition = "text")
    var blockedReason: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
