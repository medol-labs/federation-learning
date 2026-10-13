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
    override var projectionUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
