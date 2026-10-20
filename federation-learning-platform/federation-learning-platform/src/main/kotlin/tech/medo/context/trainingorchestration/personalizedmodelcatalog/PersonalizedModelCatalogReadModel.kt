package tech.medo.trainingorchestration.personalizedmodelcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.math.BigDecimal
import tech.medo.trainingorchestration.domain.states.PersonalizedModelResultStateEnum
import java.time.LocalDateTime
import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class PersonalizedModelCatalogReadModelQuery

class PersonalizedModelCatalogReadModelCriteria {
    var personalizedModelId: StringFilter? = null
    var modelUpdateSubmissionId: StringFilter? = null
    var trainingJobId: StringFilter? = null
    var trainingRunConfigurationId: StringFilter? = null
    var roundId: StringFilter? = null
    var roundNumber: IntegerFilter? = null
    var runtimeId: StringFilter? = null
    var featureSchemaId: StringFilter? = null
    var personalizationMode: StringFilter? = null
    var personalizationScope: StringFilter? = null
    var personalizedArtifactPolicy: StringFilter? = null
    var personalizationMetric: StringFilter? = null
    var minimumPersonalizationLift: BigDecimalFilter? = null
    var personalizationTargetType: StringFilter? = null
    var personalizationTargetId: StringFilter? = null
    var personalizationCohortId: StringFilter? = null
    var personalizationProfileId: StringFilter? = null
    var personalizedModelArtifactRef: StringFilter? = null
    var personalizedModelArtifactDigest: StringFilter? = null
    var personalizedModelStorageLocation: StringFilter? = null
    var platformManagedArtifactRef: StringFilter? = null
    var personalizedMetricsArtifactRef: StringFilter? = null
    var personalizedAccuracy: BigDecimalFilter? = null
    var personalizationLift: BigDecimalFilter? = null
    var state: Filter<PersonalizedModelResultStateEnum>? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class PersonalizedModelCatalogReadModelProjection : MetadataProjection {
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

fun PersonalizedModelCatalogReadModelProjection.toReadModel(): PersonalizedModelCatalogReadModel =
    PersonalizedModelCatalogReadModel(
    personalizedModelId = personalizedModelId,
    modelUpdateSubmissionId = modelUpdateSubmissionId,
    trainingJobId = trainingJobId,
    trainingRunConfigurationId = trainingRunConfigurationId,
    roundId = roundId,
    roundNumber = roundNumber,
    runtimeId = runtimeId,
    featureSchemaId = featureSchemaId,
    personalizationMode = personalizationMode,
    personalizationScope = personalizationScope,
    personalizedArtifactPolicy = personalizedArtifactPolicy,
    personalizationMetric = personalizationMetric,
    minimumPersonalizationLift = minimumPersonalizationLift,
    personalizationTargetType = personalizationTargetType,
    personalizationTargetId = personalizationTargetId,
    personalizationCohortId = personalizationCohortId,
    personalizationProfileId = personalizationProfileId,
    personalizedModelArtifactRef = personalizedModelArtifactRef,
    personalizedModelArtifactDigest = personalizedModelArtifactDigest,
    personalizedModelStorageLocation = personalizedModelStorageLocation,
    platformManagedArtifactRef = platformManagedArtifactRef,
    personalizedMetricsArtifactRef = personalizedMetricsArtifactRef,
    personalizedAccuracy = personalizedAccuracy,
    personalizationLift = personalizationLift,
    state = state,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface PersonalizedModelCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<PersonalizedModelCatalogReadModel>
    fun findAllByCriteria(criteria: PersonalizedModelCatalogReadModelCriteria?, pageable: Pageable): Page<PersonalizedModelCatalogReadModel>
    fun findById(id: UUID): PersonalizedModelCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): PersonalizedModelCatalogReadModel?
    fun findProjectionById(id: UUID): PersonalizedModelCatalogReadModelProjection?
    fun save(projection: PersonalizedModelCatalogReadModelProjection)
}

data class PersonalizedModelCatalogReadModel(
    val personalizedModelId: UUID?,
    val modelUpdateSubmissionId: UUID?,
    val trainingJobId: UUID?,
    val trainingRunConfigurationId: UUID?,
    val roundId: UUID?,
    val roundNumber: Int?,
    val runtimeId: UUID?,
    val featureSchemaId: UUID?,
    val personalizationMode: String?,
    val personalizationScope: String?,
    val personalizedArtifactPolicy: String?,
    val personalizationMetric: String?,
    val minimumPersonalizationLift: BigDecimal?,
    val personalizationTargetType: String?,
    val personalizationTargetId: UUID?,
    val personalizationCohortId: UUID?,
    val personalizationProfileId: UUID?,
    val personalizedModelArtifactRef: String?,
    val personalizedModelArtifactDigest: String?,
    val personalizedModelStorageLocation: String?,
    val platformManagedArtifactRef: String?,
    val personalizedMetricsArtifactRef: String?,
    val personalizedAccuracy: BigDecimal?,
    val personalizationLift: BigDecimal?,
    val state: PersonalizedModelResultStateEnum?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
