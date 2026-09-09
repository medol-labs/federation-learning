package tech.medo.runtimeagentoperations.datasetreadiness

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class DatasetReadinessReadModelQuery

class DatasetReadinessReadModelCriteria {
    var datasetId: StringFilter? = null
    var organizationId: StringFilter? = null
    var runtimeId: StringFilter? = null
    var featureSchemaId: StringFilter? = null
    var datasetName: StringFilter? = null
    var organizationName: StringFilter? = null
    var featureDomain: StringFilter? = null
    var featureSchemaVersion: StringFilter? = null
    var datasetUsage: StringFilter? = null
    var metadataStatus: StringFilter? = null
    var contractStatus: StringFilter? = null
    var approvalStatus: StringFilter? = null
    var accessStatus: StringFilter? = null
    var runtimeStatus: StringFilter? = null
    var overallReadiness: StringFilter? = null
    var readyForTraining: BooleanFilter? = null
    var canBeSelectedForTraining: BooleanFilter? = null
    var readinessScore: IntegerFilter? = null
    var sampleCount: IntegerFilter? = null
    var featureCount: IntegerFilter? = null
    var schemaCompatible: BooleanFilter? = null
    var labelCompatible: BooleanFilter? = null
    var qualityScore: BigDecimalFilter? = null
    var nonIidScore: BigDecimalFilter? = null
    var classBalanceScore: BigDecimalFilter? = null
    var metadataReportId: StringFilter? = null
    var datasetAccessValidationId: StringFilter? = null
    var readable: BooleanFilter? = null
    var schemaReadable: BooleanFilter? = null
    var sampleBatchReadable: BooleanFilter? = null
    var lastProfiledAt: RangeFilter<LocalDateTime>? = null
    var lastAccessValidatedAt: RangeFilter<LocalDateTime>? = null
    var lastRuntimeHeartbeatAt: RangeFilter<LocalDateTime>? = null
    var lastUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class DatasetReadinessReadModelProjection : MetadataProjection {
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
    var datasetName: String? = null
    var organizationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetUsage: String? = null
    var metadataStatus: String? = null
    var contractStatus: String? = null
    var approvalStatus: String? = null
    var accessStatus: String? = null
    var runtimeStatus: String? = null
    var overallReadiness: String? = null
    var readyForTraining: Boolean? = null
    var canBeSelectedForTraining: Boolean? = null
    var readinessScore: Int? = null
    var missingRequirements: List<String> = emptyList()
    var blockingReasons: List<String> = emptyList()
    var warnings: List<String> = emptyList()
    var sampleCount: Int? = null
    var featureCount: Int? = null
    var schemaCompatible: Boolean? = null
    var labelCompatible: Boolean? = null
    var qualityScore: BigDecimal? = null
    var nonIidScore: BigDecimal? = null
    var classBalanceScore: BigDecimal? = null
    var metadataReportId: UUID? = null
    var datasetAccessValidationId: UUID? = null
    var readable: Boolean? = null
    var schemaReadable: Boolean? = null
    var sampleBatchReadable: Boolean? = null
    var lastProfiledAt: LocalDateTime? = null
    var lastAccessValidatedAt: LocalDateTime? = null
    var lastRuntimeHeartbeatAt: LocalDateTime? = null
    var lastUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun DatasetReadinessReadModelProjection.toReadModel(): DatasetReadinessReadModel =
    DatasetReadinessReadModel(
    datasetId = datasetId,
    organizationId = organizationId,
    runtimeId = runtimeId,
    featureSchemaId = featureSchemaId,
    datasetName = datasetName,
    organizationName = organizationName,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    datasetUsage = datasetUsage,
    metadataStatus = metadataStatus,
    contractStatus = contractStatus,
    approvalStatus = approvalStatus,
    accessStatus = accessStatus,
    runtimeStatus = runtimeStatus,
    overallReadiness = overallReadiness,
    readyForTraining = readyForTraining,
    canBeSelectedForTraining = canBeSelectedForTraining,
    readinessScore = readinessScore,
    missingRequirements = missingRequirements,
    blockingReasons = blockingReasons,
    warnings = warnings,
    sampleCount = sampleCount,
    featureCount = featureCount,
    schemaCompatible = schemaCompatible,
    labelCompatible = labelCompatible,
    qualityScore = qualityScore,
    nonIidScore = nonIidScore,
    classBalanceScore = classBalanceScore,
    metadataReportId = metadataReportId,
    datasetAccessValidationId = datasetAccessValidationId,
    readable = readable,
    schemaReadable = schemaReadable,
    sampleBatchReadable = sampleBatchReadable,
    lastProfiledAt = lastProfiledAt,
    lastAccessValidatedAt = lastAccessValidatedAt,
    lastRuntimeHeartbeatAt = lastRuntimeHeartbeatAt,
    lastUpdatedAt = lastUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface DatasetReadinessReadModelRepository {
    fun findAll(pageable: Pageable): Page<DatasetReadinessReadModel>
    fun findAllByCriteria(criteria: DatasetReadinessReadModelCriteria?, pageable: Pageable): Page<DatasetReadinessReadModel>
    fun findById(id: UUID): DatasetReadinessReadModel?
    fun findProjectionById(id: UUID): DatasetReadinessReadModelProjection?
    fun save(projection: DatasetReadinessReadModelProjection)
}

data class DatasetReadinessReadModel(
    val datasetId: UUID?,
    val organizationId: UUID?,
    val runtimeId: UUID?,
    val featureSchemaId: UUID?,
    val datasetName: String?,
    val organizationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetUsage: String?,
    val metadataStatus: String?,
    val contractStatus: String?,
    val approvalStatus: String?,
    val accessStatus: String?,
    val runtimeStatus: String?,
    val overallReadiness: String?,
    val readyForTraining: Boolean?,
    val canBeSelectedForTraining: Boolean?,
    val readinessScore: Int?,
    val missingRequirements: List<String>,
    val blockingReasons: List<String>,
    val warnings: List<String>,
    val sampleCount: Int?,
    val featureCount: Int?,
    val schemaCompatible: Boolean?,
    val labelCompatible: Boolean?,
    val qualityScore: BigDecimal?,
    val nonIidScore: BigDecimal?,
    val classBalanceScore: BigDecimal?,
    val metadataReportId: UUID?,
    val datasetAccessValidationId: UUID?,
    val readable: Boolean?,
    val schemaReadable: Boolean?,
    val sampleBatchReadable: Boolean?,
    val lastProfiledAt: LocalDateTime?,
    val lastAccessValidatedAt: LocalDateTime?,
    val lastRuntimeHeartbeatAt: LocalDateTime?,
    val lastUpdatedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
