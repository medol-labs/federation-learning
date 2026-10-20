package tech.medo.dataexchange.dataimportjob

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


class DataImportJobCatalogReadModelQuery

class DataImportJobCatalogReadModelCriteria {
    var dataImportJobId: StringFilter? = null
    var commandName: StringFilter? = null
    var resourceName: StringFilter? = null
    var originalFileName: StringFilter? = null
    var filePath: StringFilter? = null
    var requestedLocale: StringFilter? = null
    var requestedAt: RangeFilter<LocalDateTime>? = null
    var requestHash: StringFilter? = null
    var status: StringFilter? = null
    var totalRows: LongFilter? = null
    var successRows: LongFilter? = null
    var failedRows: LongFilter? = null
    var failureIndexPath: StringFilter? = null
    var errorMessage: StringFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class DataImportJobCatalogReadModelProjection : MetadataProjection {
    var dataImportJobId: UUID? = null
    var commandName: String? = null
    var resourceName: String? = null
    var originalFileName: String? = null
    var filePath: String? = null
    var requestedLocale: String? = null
    var requestedAt: LocalDateTime? = null
    var requestHash: String? = null
    var status: String? = null
    var totalRows: Long? = null
    var successRows: Long? = null
    var failedRows: Long? = null
    var failureIndexPath: String? = null
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

fun DataImportJobCatalogReadModelProjection.toReadModel(): DataImportJobCatalogReadModel =
    DataImportJobCatalogReadModel(
    dataImportJobId = dataImportJobId,
    commandName = commandName,
    resourceName = resourceName,
    originalFileName = originalFileName,
    filePath = filePath,
    requestedLocale = requestedLocale,
    requestedAt = requestedAt,
    requestHash = requestHash,
    status = status,
    totalRows = totalRows,
    successRows = successRows,
    failedRows = failedRows,
    failureIndexPath = failureIndexPath,
    errorMessage = errorMessage,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface DataImportJobCatalogReadModelRepository {
    fun findAllByFilter(commandName: String?, resourceName: String?, status: String?, pageable: Pageable): Page<DataImportJobCatalogReadModel>
    fun findAllByCriteria(criteria: DataImportJobCatalogReadModelCriteria?, pageable: Pageable): Page<DataImportJobCatalogReadModel>
    fun findById(id: UUID): DataImportJobCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): DataImportJobCatalogReadModel?
    fun findProjectionById(id: UUID): DataImportJobCatalogReadModelProjection?
    fun save(projection: DataImportJobCatalogReadModelProjection)
}

data class DataImportJobCatalogReadModel(
    val dataImportJobId: UUID?,
    val commandName: String?,
    val resourceName: String?,
    val originalFileName: String?,
    val filePath: String?,
    val requestedLocale: String?,
    val requestedAt: LocalDateTime?,
    val requestHash: String?,
    val status: String?,
    val totalRows: Long?,
    val successRows: Long?,
    val failedRows: Long?,
    val failureIndexPath: String?,
    val errorMessage: String?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
