package tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeDatasetBindingCatalogReadModelQuery

class RuntimeDatasetBindingCatalogReadModelCriteria {
    var runtimeDatasetBindingId: StringFilter? = null
    var datasetId: StringFilter? = null
    var organizationId: StringFilter? = null
    var runtimeId: StringFilter? = null
    var organizationName: StringFilter? = null
    var featureSchemaId: StringFilter? = null
    var featureDomain: StringFilter? = null
    var featureSchemaVersion: StringFilter? = null
    var datasetName: StringFilter? = null
    var runtimeName: StringFilter? = null
    var filePath: StringFilter? = null
    var dataFormat: StringFilter? = null
    var configuredAt: RangeFilter<LocalDateTime>? = null
}


class RuntimeDatasetBindingCatalogReadModelProjection : MetadataProjection {
    var runtimeDatasetBindingId: UUID? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var organizationName: String? = null
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetName: String? = null
    var runtimeName: String? = null
    var filePath: String? = null
    var dataFormat: String? = null
    var configuredAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeDatasetBindingCatalogReadModelProjection.toReadModel(): RuntimeDatasetBindingCatalogReadModel =
    RuntimeDatasetBindingCatalogReadModel(
    runtimeDatasetBindingId = runtimeDatasetBindingId,
    datasetId = datasetId,
    organizationId = organizationId,
    runtimeId = runtimeId,
    organizationName = organizationName,
    featureSchemaId = featureSchemaId,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    datasetName = datasetName,
    runtimeName = runtimeName,
    filePath = filePath,
    dataFormat = dataFormat,
    configuredAt = configuredAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeDatasetBindingCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel>
    fun findAllByCriteria(criteria: RuntimeDatasetBindingCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel>
    fun findById(id: UUID): RuntimeDatasetBindingCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeDatasetBindingCatalogReadModelProjection?
    fun save(projection: RuntimeDatasetBindingCatalogReadModelProjection)
}

data class RuntimeDatasetBindingCatalogReadModel(
    val runtimeDatasetBindingId: UUID?,
    val datasetId: UUID?,
    val organizationId: UUID?,
    val runtimeId: UUID?,
    val organizationName: String?,
    val featureSchemaId: UUID?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String?,
    val runtimeName: String?,
    val filePath: String?,
    val dataFormat: String?,
    val configuredAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
