package tech.medo.fileupload.infrastructure.secondary.persistence.uploadedfilecatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.fileupload.domain.states.UploadedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "uploaded_file_catalog")
class UploadedFileCatalogReadModelEntity : MetadataProjection {
    @Id
    var fileId: UUID? = null
    var originalFileName: String? = null
    var contentType: String? = null
    var sizeBytes: Long? = null
    var purpose: String? = null
    var fileLocation: String? = null
    var checksum: String? = null
    @Enumerated(EnumType.STRING)
    var state: UploadedFileStateEnum? = null
    var uploadedAt: LocalDateTime? = null
    var referencedAt: LocalDateTime? = null
    var referencedByContext: String? = null
    var referencedByCommand: String? = null
    var referencedByCommandId: UUID? = null
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
