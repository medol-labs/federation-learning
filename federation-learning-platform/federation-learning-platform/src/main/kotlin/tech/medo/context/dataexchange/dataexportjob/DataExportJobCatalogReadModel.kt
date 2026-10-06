package tech.medo.dataexchange.dataexportjob

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.jhipster.service.filter.InstantFilter
import tech.jhipster.service.filter.LongFilter
import tech.jhipster.service.filter.StringFilter
import tech.medo.shared.application.metadata.MetadataProjection
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

class DataExportJobCatalogReadModelQuery

class DataExportJobCatalogReadModelCriteria {
    var dataExportJobId: StringFilter? = null
    var resourceName: StringFilter? = null
    var requestedLocale: StringFilter? = null
    var requestedAt: InstantFilter? = null
    var snapshotUpperBound: InstantFilter? = null
    var requestHash: StringFilter? = null
    var fileName: StringFilter? = null
    var filePath: StringFilter? = null
    var rowCount: LongFilter? = null
    var errorMessage: StringFilter? = null
    var status: StringFilter? = null
}

class DataExportJobCatalogReadModelProjection : MetadataProjection {
    override var projectionUpdatedAt: LocalDateTime? = null
    var dataExportJobId: UUID? = null
    var resourceName: String? = null
    var criteriaJson: String? = null
    var sortJson: String? = null
    var columnsJson: String? = null
    var requestedLocale: String? = null
    var requestedAt: Instant? = null
    var snapshotUpperBound: Instant? = null
    var requestHash: String? = null
    var fileName: String? = null
    var filePath: String? = null
    var rowCount: Long? = null
    var errorMessage: String? = null
    var status: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
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
        fileName = fileName,
        filePath = filePath,
        rowCount = rowCount,
        errorMessage = errorMessage,
        status = status,
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
    val requestedAt: Instant?,
    val snapshotUpperBound: Instant?,
    val requestHash: String?,
    val fileName: String?,
    val filePath: String?,
    val rowCount: Long?,
    val errorMessage: String?,
    val status: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
