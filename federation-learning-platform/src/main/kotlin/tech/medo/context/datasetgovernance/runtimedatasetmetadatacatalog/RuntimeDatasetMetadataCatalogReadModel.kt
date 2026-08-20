package tech.medo.datasetgovernance.runtimedatasetmetadatacatalog

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


class RuntimeDatasetMetadataCatalogReadModelQuery

class RuntimeDatasetMetadataCatalogReadModelCriteria {
    var metadataReportId: StringFilter? = null
    var datasetId: StringFilter? = null
    var organizationId: StringFilter? = null
    var runtimeId: StringFilter? = null
    var featureSchemaId: StringFilter? = null
    var datasetName: StringFilter? = null
    var organizationName: StringFilter? = null
    var featureDomain: StringFilter? = null
    var featureSchemaVersion: StringFilter? = null
    var sampleCount: IntegerFilter? = null
    var featureCount: IntegerFilter? = null
    var schemaCompatible: BooleanFilter? = null
    var labelCompatible: BooleanFilter? = null
    var missingValueRate: BigDecimalFilter? = null
    var duplicateRate: BigDecimalFilter? = null
    var qualityScore: BigDecimalFilter? = null
    var nonIidScore: BigDecimalFilter? = null
    var classBalanceScore: BigDecimalFilter? = null
    var profilingStatus: StringFilter? = null
    var failureReason: StringFilter? = null
    var profiledAt: RangeFilter<LocalDateTime>? = null
}


class RuntimeDatasetMetadataCatalogReadModelProjection : MetadataProjection {
    var metadataReportId: UUID? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
    var datasetName: String? = null
    var organizationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var sampleCount: Int? = null
    var featureCount: Int? = null
    var schemaCompatible: Boolean? = null
    var labelCompatible: Boolean? = null
    var missingValueRate: BigDecimal? = null
    var duplicateRate: BigDecimal? = null
    var qualityScore: BigDecimal? = null
    var nonIidScore: BigDecimal? = null
    var classBalanceScore: BigDecimal? = null
    var profilingStatus: String? = null
    var failureReason: String? = null
    var profiledAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeDatasetMetadataCatalogReadModelProjection.toReadModel(): RuntimeDatasetMetadataCatalogReadModel =
    RuntimeDatasetMetadataCatalogReadModel(
    metadataReportId = metadataReportId,
    datasetId = datasetId,
    organizationId = organizationId,
    runtimeId = runtimeId,
    featureSchemaId = featureSchemaId,
    datasetName = datasetName,
    organizationName = organizationName,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    sampleCount = sampleCount,
    featureCount = featureCount,
    schemaCompatible = schemaCompatible,
    labelCompatible = labelCompatible,
    missingValueRate = missingValueRate,
    duplicateRate = duplicateRate,
    qualityScore = qualityScore,
    nonIidScore = nonIidScore,
    classBalanceScore = classBalanceScore,
    profilingStatus = profilingStatus,
    failureReason = failureReason,
    profiledAt = profiledAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeDatasetMetadataCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeDatasetMetadataCatalogReadModel>
    fun findAllByCriteria(criteria: RuntimeDatasetMetadataCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeDatasetMetadataCatalogReadModel>
    fun findById(id: UUID): RuntimeDatasetMetadataCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeDatasetMetadataCatalogReadModelProjection?
    fun save(projection: RuntimeDatasetMetadataCatalogReadModelProjection)
}

data class RuntimeDatasetMetadataCatalogReadModel(
    val metadataReportId: UUID?,
    val datasetId: UUID?,
    val organizationId: UUID?,
    val runtimeId: UUID?,
    val featureSchemaId: UUID?,
    val datasetName: String?,
    val organizationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val sampleCount: Int?,
    val featureCount: Int?,
    val schemaCompatible: Boolean?,
    val labelCompatible: Boolean?,
    val missingValueRate: BigDecimal?,
    val duplicateRate: BigDecimal?,
    val qualityScore: BigDecimal?,
    val nonIidScore: BigDecimal?,
    val classBalanceScore: BigDecimal?,
    val profilingStatus: String?,
    val failureReason: String?,
    val profiledAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
