package tech.medo.runtimeagentoperations.roundexecutioncatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RoundExecutionCatalogReadModelQuery

class RoundExecutionCatalogReadModelCriteria {
    var roundExecutionId: StringFilter? = null
    var executionSessionId: StringFilter? = null
    var executionPlanId: StringFilter? = null
    var trainingJobId: StringFilter? = null
    var trainingRunConfigurationId: StringFilter? = null
    var roundId: StringFilter? = null
    var roundNumber: IntegerFilter? = null
    var organizationId: StringFilter? = null
    var runtimeId: StringFilter? = null
    var state: Filter<RoundExecutionStateEnum>? = null
    var featureSchemaId: StringFilter? = null
    var baseModelId: StringFilter? = null
    var runtimeEngineJobId: StringFilter? = null
    var runtimeEngineObservedStatus: StringFilter? = null
    var runtimeEngineObservationAt: RangeFilter<LocalDateTime>? = null
    var localUpdateArtifactRef: StringFilter? = null
    var metricsArtifactRef: StringFilter? = null
    var localExecutionRequirementsSatisfied: BooleanFilter? = null
    var runtimeIdentityMatched: BooleanFilter? = null
    var runtimeDatasetBindingAvailable: BooleanFilter? = null
    var datasetAccessValidated: BooleanFilter? = null
    var baseModelAvailable: BooleanFilter? = null
    var trainingConfigurationSupported: BooleanFilter? = null
    var runtimeResourceAvailable: BooleanFilter? = null
    var runtimeAgentIdle: BooleanFilter? = null
    var updateArtifactId: StringFilter? = null
    var artifactRef: StringFilter? = null
    var artifactDigest: StringFilter? = null
    var trainingLoss: BigDecimalFilter? = null
    var receivedAt: RangeFilter<LocalDateTime>? = null
    var acceptedAt: RangeFilter<LocalDateTime>? = null
    var rejectedAt: RangeFilter<LocalDateTime>? = null
    var startedAt: RangeFilter<LocalDateTime>? = null
    var completedAt: RangeFilter<LocalDateTime>? = null
    var failedAt: RangeFilter<LocalDateTime>? = null
    var submittedAt: RangeFilter<LocalDateTime>? = null
    var failureReason: StringFilter? = null
    var retryReason: StringFilter? = null
    var runtimeEngineReleased: BooleanFilter? = null
    var runtimeEngineReleaseFailureReason: StringFilter? = null
}


class RoundExecutionCatalogReadModelProjection : MetadataProjection {
    var roundExecutionId: UUID? = null
    var executionSessionId: UUID? = null
    var executionPlanId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var roundId: UUID? = null
    var roundNumber: Int? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
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
    var failureReason: String? = null
    var retryReason: String? = null
    var runtimeEngineReleased: Boolean? = null
    var runtimeEngineReleaseFailureReason: String? = null
    var rejectionReasons: List<String> = emptyList()
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RoundExecutionCatalogReadModelProjection.toReadModel(): RoundExecutionCatalogReadModel =
    RoundExecutionCatalogReadModel(
    roundExecutionId = roundExecutionId,
    executionSessionId = executionSessionId,
    executionPlanId = executionPlanId,
    trainingJobId = trainingJobId,
    trainingRunConfigurationId = trainingRunConfigurationId,
    roundId = roundId,
    roundNumber = roundNumber,
    organizationId = organizationId,
    runtimeId = runtimeId,
    state = state,
    featureSchemaId = featureSchemaId,
    baseModelId = baseModelId,
    runtimeEngineJobId = runtimeEngineJobId,
    runtimeEngineObservedStatus = runtimeEngineObservedStatus,
    runtimeEngineObservationAt = runtimeEngineObservationAt,
    localUpdateArtifactRef = localUpdateArtifactRef,
    metricsArtifactRef = metricsArtifactRef,
    localExecutionRequirementsSatisfied = localExecutionRequirementsSatisfied,
    runtimeIdentityMatched = runtimeIdentityMatched,
    runtimeDatasetBindingAvailable = runtimeDatasetBindingAvailable,
    datasetAccessValidated = datasetAccessValidated,
    baseModelAvailable = baseModelAvailable,
    trainingConfigurationSupported = trainingConfigurationSupported,
    runtimeResourceAvailable = runtimeResourceAvailable,
    runtimeAgentIdle = runtimeAgentIdle,
    updateArtifactId = updateArtifactId,
    artifactRef = artifactRef,
    artifactDigest = artifactDigest,
    trainingLoss = trainingLoss,
    receivedAt = receivedAt,
    acceptedAt = acceptedAt,
    rejectedAt = rejectedAt,
    startedAt = startedAt,
    completedAt = completedAt,
    failedAt = failedAt,
    submittedAt = submittedAt,
    failureReason = failureReason,
    retryReason = retryReason,
    runtimeEngineReleased = runtimeEngineReleased,
    runtimeEngineReleaseFailureReason = runtimeEngineReleaseFailureReason,
    rejectionReasons = rejectionReasons,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RoundExecutionCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RoundExecutionCatalogReadModel>
    fun findAllByCriteria(criteria: RoundExecutionCatalogReadModelCriteria?, pageable: Pageable): Page<RoundExecutionCatalogReadModel>
    fun findById(id: UUID): RoundExecutionCatalogReadModel?
    fun findProjectionById(id: UUID): RoundExecutionCatalogReadModelProjection?
    fun save(projection: RoundExecutionCatalogReadModelProjection)
}

data class RoundExecutionCatalogReadModel(
    val roundExecutionId: UUID?,
    val executionSessionId: UUID?,
    val executionPlanId: UUID?,
    val trainingJobId: UUID?,
    val trainingRunConfigurationId: UUID?,
    val roundId: UUID?,
    val roundNumber: Int?,
    val organizationId: UUID?,
    val runtimeId: UUID?,
    val state: RoundExecutionStateEnum?,
    val featureSchemaId: UUID?,
    val baseModelId: UUID?,
    val runtimeEngineJobId: String?,
    val runtimeEngineObservedStatus: String?,
    val runtimeEngineObservationAt: LocalDateTime?,
    val localUpdateArtifactRef: String?,
    val metricsArtifactRef: String?,
    val localExecutionRequirementsSatisfied: Boolean?,
    val runtimeIdentityMatched: Boolean?,
    val runtimeDatasetBindingAvailable: Boolean?,
    val datasetAccessValidated: Boolean?,
    val baseModelAvailable: Boolean?,
    val trainingConfigurationSupported: Boolean?,
    val runtimeResourceAvailable: Boolean?,
    val runtimeAgentIdle: Boolean?,
    val updateArtifactId: UUID?,
    val artifactRef: String?,
    val artifactDigest: String?,
    val trainingLoss: BigDecimal?,
    val receivedAt: LocalDateTime?,
    val acceptedAt: LocalDateTime?,
    val rejectedAt: LocalDateTime?,
    val startedAt: LocalDateTime?,
    val completedAt: LocalDateTime?,
    val failedAt: LocalDateTime?,
    val submittedAt: LocalDateTime?,
    val failureReason: String?,
    val retryReason: String?,
    val runtimeEngineReleased: Boolean?,
    val runtimeEngineReleaseFailureReason: String?,
    val rejectionReasons: List<String>,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
