package tech.medo.trainingorchestration.infrastructure.secondary.persistence.personalizedmodelcatalogreadmodel

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
import tech.medo.trainingorchestration.domain.states.PersonalizedModelResultStateEnum
import java.time.LocalDateTime

@Entity
@Table(name = "personalized_model_catalog")
class PersonalizedModelCatalogReadModelEntity : MetadataProjection {
    @Id
    var personalizedModelId: UUID? = null
    var modelUpdateSubmissionId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var roundId: UUID? = null
    var roundNumber: Int? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
    var personalizationMode: String? = null
    var personalizationScope: String? = null
    var personalizedArtifactPolicy: String? = null
    var personalizationMetric: String? = null
    var minimumPersonalizationLift: BigDecimal? = null
    var personalizationTargetType: String? = null
    var personalizationTargetId: UUID? = null
    var personalizationCohortId: UUID? = null
    var personalizationProfileId: UUID? = null
    var personalizedModelArtifactRef: String? = null
    var personalizedModelArtifactDigest: String? = null
    var personalizedModelStorageLocation: String? = null
    var platformManagedArtifactRef: String? = null
    var personalizedMetricsArtifactRef: String? = null
    var personalizedAccuracy: BigDecimal? = null
    var personalizationLift: BigDecimal? = null
    @Enumerated(EnumType.STRING)
    var state: PersonalizedModelResultStateEnum? = null
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
