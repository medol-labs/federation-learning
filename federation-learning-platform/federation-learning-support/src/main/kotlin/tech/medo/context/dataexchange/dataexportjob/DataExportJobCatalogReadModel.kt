package tech.medo.dataexchange.dataexportjob

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.LongFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class DataExportJobCatalogReadModelQuery

class DataExportJobCatalogReadModelCriteria {
    var dataExportJobId: StringFilter? = null
    var resourceName: StringFilter? = null
    var criteriaJson: StringFilter? = null
    var sortJson: StringFilter? = null
    var columnsJson: StringFilter? = null
    var requestedLocale: StringFilter? = null
    var requestedAt: RangeFilter<LocalDateTime>? = null
    var snapshotUpperBound: RangeFilter<LocalDateTime>? = null
    var requestHash: StringFilter? = null
    var status: StringFilter? = null
    var fileName: StringFilter? = null
    var filePath: StringFilter? = null
    var downloadUri: StringFilter? = null
    var rowCount: LongFilter? = null
    var errorMessage: StringFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class DataExportJobCatalogReadModelProjection : MetadataProjection {
    var dataExportJobId: UUID? = null
    var resourceName: String? = null
    var criteriaJson: String? = null
    var sortJson: String? = null
    var columnsJson: String? = null
    var requestedLocale: String? = null
    var requestedAt: LocalDateTime? = null
    var snapshotUpperBound: LocalDateTime? = null
    var requestHash: String? = null
    var status: String? = null
    var fileName: String? = null
    var filePath: String? = null
    var downloadUri: String? = null
    var rowCount: Long? = null
    var errorMessage: String? = null
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

fun DataExportJobCatalogReadModelProjection.toReadModel(): DataExportJobCatalogReadModel =
    DataExportJobCatalogReadModel(
    dataExportJobId = dataExportJobId,
    resourceName = resourceName,
    criteriaJson = criteriaJson,
    sortJson = sortJson,
    columnsJson = columnsJson,
    requestedLocale = requestedLocale,
    requestedAt = requestedAt,
    snapshotUpperBound = snapshotUpperBound,
    requestHash = requestHash,
    status = status,
    fileName = fileName,
    filePath = filePath,
    downloadUri = downloadUri,
    rowCount = rowCount,
    errorMessage = errorMessage,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface DataExportJobCatalogReadModelRepository {
    fun findAllByFilter(status: String?, pageable: Pageable): Page<DataExportJobCatalogReadModel>
    fun findAllByCriteria(criteria: DataExportJobCatalogReadModelCriteria?, pageable: Pageable): Page<DataExportJobCatalogReadModel>
    fun findById(id: UUID): DataExportJobCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): DataExportJobCatalogReadModel?
    fun findProjectionById(id: UUID): DataExportJobCatalogReadModelProjection?
    fun save(projection: DataExportJobCatalogReadModelProjection)
}

data class DataExportJobCatalogReadModel(
    val dataExportJobId: UUID?,
    val resourceName: String?,
    val criteriaJson: String?,
    val sortJson: String?,
    val columnsJson: String?,
    val requestedLocale: String?,
    val requestedAt: LocalDateTime?,
    val snapshotUpperBound: LocalDateTime?,
    val requestHash: String?,
    val status: String?,
    val fileName: String?,
    val filePath: String?,
    val downloadUri: String?,
    val rowCount: Long?,
    val errorMessage: String?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
