package tech.medo.datasetgovernance.infrastructure.secondary.persistence.datasetcapabilityreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class DatasetCapabilityReadModelEntity : MetadataProjection {
    @Id
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
    var organizationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetName: String? = null
    var datasetUsage: String? = null
    var sampleCount: Int? = null
    var featureCount: Int? = null
    var schemaCompatible: Boolean? = null
    var labelCompatible: Boolean? = null
    var qualityScore: BigDecimal? = null
    var nonIidScore: BigDecimal? = null
    var metadataReportId: UUID? = null
    var metadataStatus: String? = null
    var approvalStatus: String? = null
    var approved: Boolean? = null
    var lastProfiledAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
