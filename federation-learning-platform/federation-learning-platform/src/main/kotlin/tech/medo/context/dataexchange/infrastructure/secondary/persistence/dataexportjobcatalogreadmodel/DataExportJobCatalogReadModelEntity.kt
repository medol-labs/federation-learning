package tech.medo.dataexchange.infrastructure.secondary.persistence.dataexportjobcatalogreadmodel

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import tech.medo.shared.application.metadata.MetadataProjection
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "data_export_job_catalog")
class DataExportJobCatalogReadModelEntity : MetadataProjection {
    override var projectionUpdatedAt: LocalDateTime? = null
    @Id
    var dataExportJobId: UUID? = null
    var resourceName: String? = null
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "text")
    var criteriaJson: String? = null
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "text")
    var sortJson: String? = null
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "text")
    var columnsJson: String? = null
    var requestedLocale: String? = null
    var requestedAt: Instant? = null
    var snapshotUpperBound: Instant? = null
    var requestHash: String? = null
    var fileName: String? = null
    var filePath: String? = null
    var rowCount: Long? = null
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "text")
    var errorMessage: String? = null
    var status: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
