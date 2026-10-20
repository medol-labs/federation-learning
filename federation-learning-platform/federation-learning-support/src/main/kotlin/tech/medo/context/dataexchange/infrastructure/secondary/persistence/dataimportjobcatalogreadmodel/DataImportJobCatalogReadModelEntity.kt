package tech.medo.dataexchange.infrastructure.secondary.persistence.dataimportjobcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@Entity
@Table(name = "data_import_job_catalog")
class DataImportJobCatalogReadModelEntity : MetadataProjection {
    @Id
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
    @Column(columnDefinition = "text")
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
