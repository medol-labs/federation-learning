package tech.medo.runtimeagentoperations.datasetcapability

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition;
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class DatasetCapabilityReadModelQuery

class DatasetCapabilityReadModelCriteria {
    var datasetId: StringFilter? = null
    var organizationId: StringFilter? = null
    var runtimeId: StringFilter? = null
    var featureSchemaId: StringFilter? = null
    var organizationName: StringFilter? = null
    var featureDomain: StringFilter? = null
    var featureSchemaVersion: StringFilter? = null
    var datasetName: StringFilter? = null
    var datasetUsage: StringFilter? = null
    var sampleCount: IntegerFilter? = null
    var featureCount: IntegerFilter? = null
    var schemaCompatible: BooleanFilter? = null
    var labelCompatible: BooleanFilter? = null
    var qualityScore: BigDecimalFilter? = null
    var nonIidScore: BigDecimalFilter? = null
    var metadataReportId: StringFilter? = null
    var metadataStatus: StringFilter? = null
    var contractStatus: StringFilter? = null
    var approvalStatus: StringFilter? = null
    var approved: BooleanFilter? = null
    var lastProfiledAt: RangeFilter<LocalDateTime>? = null
}


class DatasetCapabilityReadModelProjection : MetadataProjection {
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
    var features: List<FeatureDefinition> = emptyList()
    var labels: List<LabelDefinition> = emptyList()
    var organizationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetName: String? = null
    var datasetUsage: String? = null
    var sampleCount: Int? = null
    var featureCount: Int? = null
    var schemaCompatible: Boolean? = null
    var labelCompatible: Boolean? = null
    var qualityScore: BigDecimal? = null
    var nonIidScore: BigDecimal? = null
    var metadataReportId: UUID? = null
    var metadataStatus: String? = null
    var contractStatus: String? = null
    var approvalStatus: String? = null
    var approved: Boolean? = null
    var lastProfiledAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun DatasetCapabilityReadModelProjection.toReadModel(): DatasetCapabilityReadModel =
    DatasetCapabilityReadModel(
    datasetId = datasetId,
    organizationId = organizationId,
    runtimeId = runtimeId,
    featureSchemaId = featureSchemaId,
    features = features,
    labels = labels,
    organizationName = organizationName,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    datasetName = datasetName,
    datasetUsage = datasetUsage,
    sampleCount = sampleCount,
    featureCount = featureCount,
    schemaCompatible = schemaCompatible,
    labelCompatible = labelCompatible,
    qualityScore = qualityScore,
    nonIidScore = nonIidScore,
    metadataReportId = metadataReportId,
    metadataStatus = metadataStatus,
    contractStatus = contractStatus,
    approvalStatus = approvalStatus,
    approved = approved,
    lastProfiledAt = lastProfiledAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface DatasetCapabilityReadModelRepository {
    fun findAll(pageable: Pageable): Page<DatasetCapabilityReadModel>
    fun findAllByCriteria(criteria: DatasetCapabilityReadModelCriteria?, pageable: Pageable): Page<DatasetCapabilityReadModel>
    fun findById(id: UUID): DatasetCapabilityReadModel?
    fun findProjectionById(id: UUID): DatasetCapabilityReadModelProjection?
    fun save(projection: DatasetCapabilityReadModelProjection)
}

data class DatasetCapabilityReadModel(
    val datasetId: UUID?,
    val organizationId: UUID?,
    val runtimeId: UUID?,
    val featureSchemaId: UUID?,
    val features: List<FeatureDefinition>,
    val labels: List<LabelDefinition>,
    val organizationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String?,
    val datasetUsage: String?,
    val sampleCount: Int?,
    val featureCount: Int?,
    val schemaCompatible: Boolean?,
    val labelCompatible: Boolean?,
    val qualityScore: BigDecimal?,
    val nonIidScore: BigDecimal?,
    val metadataReportId: UUID?,
    val metadataStatus: String?,
    val contractStatus: String?,
    val approvalStatus: String?,
    val approved: Boolean?,
    val lastProfiledAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
