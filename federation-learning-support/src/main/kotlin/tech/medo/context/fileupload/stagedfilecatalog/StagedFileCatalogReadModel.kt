package tech.medo.fileupload.stagedfilecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.fileupload.domain.states.StagedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class StagedFileCatalogReadModelQuery

class StagedFileCatalogReadModelProjection : MetadataProjection {
    var stagedFileId: UUID? = null
    var originalFileName: String? = null
    var contentType: String? = null
    var sizeBytes: Long? = null
    var purpose: String? = null
    var stagedFileLocation: String? = null
    var checksum: String? = null
    var state: StagedFileStateEnum? = null
    var stagedAt: LocalDateTime? = null
    var consumedAt: LocalDateTime? = null
    var consumedByContext: String? = null
    var consumedByCommand: String? = null
    var consumedByCommandId: UUID? = null
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

fun StagedFileCatalogReadModelProjection.toReadModel(): StagedFileCatalogReadModel =
    StagedFileCatalogReadModel(
    stagedFileId = stagedFileId,
    originalFileName = originalFileName,
    contentType = contentType,
    sizeBytes = sizeBytes,
    purpose = purpose,
    stagedFileLocation = stagedFileLocation,
    checksum = checksum,
    state = state,
    stagedAt = stagedAt,
    consumedAt = consumedAt,
    consumedByContext = consumedByContext,
    consumedByCommand = consumedByCommand,
    consumedByCommandId = consumedByCommandId,
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

interface StagedFileCatalogReadModelRepository {
    fun findAllByFilter(purpose: String?, pageable: Pageable): Page<StagedFileCatalogReadModel>
    fun findById(id: UUID): StagedFileCatalogReadModel?
    fun findProjectionById(id: UUID): StagedFileCatalogReadModelProjection?
    fun save(projection: StagedFileCatalogReadModelProjection)
}

data class StagedFileCatalogReadModel(
    val stagedFileId: UUID?,
    val originalFileName: String?,
    val contentType: String?,
    val sizeBytes: Long?,
    val purpose: String?,
    val stagedFileLocation: String?,
    val checksum: String?,
    val state: StagedFileStateEnum?,
    val stagedAt: LocalDateTime?,
    val consumedAt: LocalDateTime?,
    val consumedByContext: String?,
    val consumedByCommand: String?,
    val consumedByCommandId: UUID?,
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
