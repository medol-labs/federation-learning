package tech.medo.fileupload.uploadedfilecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.fileupload.domain.states.UploadedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.LongFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class UploadedFileCatalogReadModelQuery

class UploadedFileCatalogReadModelCriteria {
    var fileId: StringFilter? = null
    var originalFileName: StringFilter? = null
    var contentType: StringFilter? = null
    var sizeBytes: LongFilter? = null
    var purpose: StringFilter? = null
    var fileLocation: StringFilter? = null
    var checksum: StringFilter? = null
    var state: Filter<UploadedFileStateEnum>? = null
    var uploadedAt: RangeFilter<LocalDateTime>? = null
    var referencedAt: RangeFilter<LocalDateTime>? = null
    var referencedByContext: StringFilter? = null
    var referencedByCommand: StringFilter? = null
    var referencedByCommandId: StringFilter? = null
    var discardedAt: RangeFilter<LocalDateTime>? = null
    var discardReason: StringFilter? = null
    var expiresAt: RangeFilter<LocalDateTime>? = null
    var expiredAt: RangeFilter<LocalDateTime>? = null
    var expirationReason: StringFilter? = null
}


class UploadedFileCatalogReadModelProjection : MetadataProjection {
    var fileId: UUID? = null
    var originalFileName: String? = null
    var contentType: String? = null
    var sizeBytes: Long? = null
    var purpose: String? = null
    var fileLocation: String? = null
    var checksum: String? = null
    var state: UploadedFileStateEnum? = null
    var uploadedAt: LocalDateTime? = null
    var referencedAt: LocalDateTime? = null
    var referencedByContext: String? = null
    var referencedByCommand: String? = null
    var referencedByCommandId: UUID? = null
    var discardedAt: LocalDateTime? = null
    var discardReason: String? = null
    var expiresAt: LocalDateTime? = null
    var expiredAt: LocalDateTime? = null
    var expirationReason: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun UploadedFileCatalogReadModelProjection.toReadModel(): UploadedFileCatalogReadModel =
    UploadedFileCatalogReadModel(
    fileId = fileId,
    originalFileName = originalFileName,
    contentType = contentType,
    sizeBytes = sizeBytes,
    purpose = purpose,
    fileLocation = fileLocation,
    checksum = checksum,
    state = state,
    uploadedAt = uploadedAt,
    referencedAt = referencedAt,
    referencedByContext = referencedByContext,
    referencedByCommand = referencedByCommand,
    referencedByCommandId = referencedByCommandId,
    discardedAt = discardedAt,
    discardReason = discardReason,
    expiresAt = expiresAt,
    expiredAt = expiredAt,
    expirationReason = expirationReason,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface UploadedFileCatalogReadModelRepository {
    fun findAllByFilter(purpose: String?, pageable: Pageable): Page<UploadedFileCatalogReadModel>
    fun findAllByCriteria(criteria: UploadedFileCatalogReadModelCriteria?, pageable: Pageable): Page<UploadedFileCatalogReadModel>
    fun findById(id: UUID): UploadedFileCatalogReadModel?
    fun findProjectionById(id: UUID): UploadedFileCatalogReadModelProjection?
    fun save(projection: UploadedFileCatalogReadModelProjection)
}

data class UploadedFileCatalogReadModel(
    val fileId: UUID?,
    val originalFileName: String?,
    val contentType: String?,
    val sizeBytes: Long?,
    val purpose: String?,
    val fileLocation: String?,
    val checksum: String?,
    val state: UploadedFileStateEnum?,
    val uploadedAt: LocalDateTime?,
    val referencedAt: LocalDateTime?,
    val referencedByContext: String?,
    val referencedByCommand: String?,
    val referencedByCommandId: UUID?,
    val discardedAt: LocalDateTime?,
    val discardReason: String?,
    val expiresAt: LocalDateTime?,
    val expiredAt: LocalDateTime?,
    val expirationReason: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
