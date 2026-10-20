package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimetelemetrylatestreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@Entity
@Table(name = "runtime_telemetry_latest")
class RuntimeTelemetryLatestReadModelEntity : MetadataProjection {
    @Id
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var federationId: UUID? = null
    var federationName: String? = null
    var trainingJobId: UUID? = null
    var trainingJobObjective: String? = null
    var roundExecutionId: UUID? = null
    var runtimeNodeName: String? = null
    var cpuLoad: BigDecimal? = null
    var gpuLoad: BigDecimal? = null
    var memoryLoad: BigDecimal? = null
    var lastHeartbeatAt: LocalDateTime? = null
    var lastRecoveredAt: LocalDateTime? = null
    var offlineDetectionPending: Boolean? = null
    var recoveryDetectionPending: Boolean? = null
    var resourcePressureDetectionPending: Boolean? = null
    @Column(columnDefinition = "text")
    var offlineReason: String? = null
    @Column(columnDefinition = "text")
    var recoveryReason: String? = null
    var pressureType: String? = null
    var observedValue: BigDecimal? = null
    var thresholdValue: BigDecimal? = null
    var alertSeverity: String? = null
    @Column(columnDefinition = "text")
    var alertMessage: String? = null
    var healthStatus: String? = null
    var telemetryRetentionPolicy: String? = null
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
