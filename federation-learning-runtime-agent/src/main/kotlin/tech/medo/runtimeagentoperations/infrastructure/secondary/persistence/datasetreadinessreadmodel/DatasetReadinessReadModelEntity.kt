package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetreadinessreadmodel

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
class DatasetReadinessReadModelEntity : MetadataProjection {
    @Id
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
    var datasetName: String? = null
    var organizationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetUsage: String? = null
    var metadataStatus: String? = null
    var contractStatus: String? = null
    var approvalStatus: String? = null
    var accessStatus: String? = null
    var runtimeStatus: String? = null
    var overallReadiness: String? = null
    var readyForTraining: Boolean? = null
    var canBeSelectedForTraining: Boolean? = null
    var readinessScore: Int? = null
    @Column(columnDefinition = "text")
    var missingRequirements: String? = null
    @Column(columnDefinition = "text")
    var blockingReasons: String? = null
    @Column(columnDefinition = "text")
    var warnings: String? = null
    var sampleCount: Int? = null
    var featureCount: Int? = null
    var schemaCompatible: Boolean? = null
    var labelCompatible: Boolean? = null
    var qualityScore: BigDecimal? = null
    var nonIidScore: BigDecimal? = null
    var classBalanceScore: BigDecimal? = null
    var metadataReportId: UUID? = null
    var datasetAccessValidationId: UUID? = null
    var readable: Boolean? = null
    var schemaReadable: Boolean? = null
    var sampleBatchReadable: Boolean? = null
    var lastProfiledAt: LocalDateTime? = null
    var lastAccessValidatedAt: LocalDateTime? = null
    var lastRuntimeHeartbeatAt: LocalDateTime? = null
    var lastUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
