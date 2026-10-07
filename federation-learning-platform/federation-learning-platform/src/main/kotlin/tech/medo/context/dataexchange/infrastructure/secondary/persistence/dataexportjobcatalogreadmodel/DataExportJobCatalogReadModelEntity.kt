package tech.medo.dataexchange.infrastructure.secondary.persistence.dataexportjobcatalogreadmodel

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
@Table(name = "data_export_job_catalog")
class DataExportJobCatalogReadModelEntity : MetadataProjection {
    @Id
    var dataExportJobId: UUID? = null
    var resourceName: String? = null
    @Column(columnDefinition = "text")
    var criteriaJson: String? = null
    @Column(columnDefinition = "text")
    var sortJson: String? = null
    @Column(columnDefinition = "text")
    var columnsJson: String? = null
    var requestedLocale: String? = null
    var requestedAt: LocalDateTime? = null
    var snapshotUpperBound: LocalDateTime? = null
    var requestHash: String? = null
    var status: String? = null
    var fileName: String? = null
    var filePath: String? = null
    var rowCount: Long? = null
    @Column(columnDefinition = "text")
    var errorMessage: String? = null
    override var projectionUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
