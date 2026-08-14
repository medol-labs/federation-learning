package tech.medo.fileupload.infrastructure.secondary.persistence.stagedfilecatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.fileupload.domain.states.StagedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class StagedFileCatalogReadModelEntity : MetadataProjection {
    @Id
    var stagedFileId: UUID? = null
    var originalFileName: String? = null
    var contentType: String? = null
    var sizeBytes: Long? = null
    var purpose: String? = null
    var stagedFileLocation: String? = null
    var checksum: String? = null
    @Enumerated(EnumType.STRING)
    var state: StagedFileStateEnum? = null
    var stagedAt: LocalDateTime? = null
    var consumedAt: LocalDateTime? = null
    var consumedByContext: String? = null
    var consumedByCommand: String? = null
    var consumedByCommandId: UUID? = null
    var discardedAt: LocalDateTime? = null
    @Column(columnDefinition = "text")
    var discardReason: String? = null
    var expiresAt: LocalDateTime? = null
    var expiredAt: LocalDateTime? = null
    @Column(columnDefinition = "text")
    var expirationReason: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
