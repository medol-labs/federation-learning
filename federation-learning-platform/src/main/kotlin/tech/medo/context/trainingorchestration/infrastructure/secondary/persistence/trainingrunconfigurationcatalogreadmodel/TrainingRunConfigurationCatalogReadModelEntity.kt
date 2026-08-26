package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingrunconfigurationcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum;


@Entity
class TrainingRunConfigurationCatalogReadModelEntity : MetadataProjection {
    @Id
    var trainingRunConfigurationId: UUID? = null
    var federationId: UUID? = null
    var featureSchemaId: UUID? = null
    var initialModelId: UUID? = null
    var initialModelName: String? = null
    var initialModelVersion: String? = null
    var federationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var initialModelArtifactUri: String? = null
    var initialModelRegistryRef: String? = null
    var initialModelFormat: String? = null
    var initialModelArtifactDigest: String? = null
    var initialModelSignatureUri: String? = null
    var strategyName: String? = null
    var aggregationAlgorithm: String? = null
    var maxRounds: Int? = null
    var minimumNodesPerRound: Int? = null
    var roundTimeoutSeconds: Int? = null
    var nodeResponseTimeoutSeconds: Int? = null
    var localEpochs: Int? = null
    var batchSize: Int? = null
    var learningRate: BigDecimal? = null
    var optimizer: String? = null
    var lossFunction: String? = null
    var gradientClippingNorm: BigDecimal? = null
    var secureAggregationRequired: Boolean? = null
    var minimumAccuracy: BigDecimal? = null
    var minimumFairnessScore: BigDecimal? = null
    @Column(columnDefinition = "text")
    var updateReason: String? = null
    var lockedByTrainingJobId: UUID? = null
    @Enumerated(EnumType.STRING)
    var state: TrainingRunConfigurationStateEnum? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
