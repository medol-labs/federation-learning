package tech.medo.modellifecycle.infrastructure.secondary.persistence.modelcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.math.BigDecimal
import tech.medo.modellifecycle.domain.states.ModelStateEnum

import java.time.LocalDateTime

@Entity
@Table(name = "model_catalog")
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
    var experimentId: UUID? = null
    var hyperparameterSnapshotId: UUID? = null
    var reproducibilityManifestId: UUID? = null
    var modelCardId: UUID? = null
    override var projectionUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
