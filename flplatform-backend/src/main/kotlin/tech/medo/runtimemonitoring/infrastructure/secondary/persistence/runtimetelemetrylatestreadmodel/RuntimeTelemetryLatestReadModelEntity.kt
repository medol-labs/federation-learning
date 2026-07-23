package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimetelemetrylatestreadmodel

import jakarta.persistence.Entity
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
class RuntimeTelemetryLatestReadModelEntity : MetadataProjection {
    @Id
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var federationId: UUID? = null
    var trainingJobId: UUID? = null
    var roundExecutionId: UUID? = null
    var cpuLoad: BigDecimal? = null
    var gpuLoad: BigDecimal? = null
    var memoryLoad: BigDecimal? = null
    var lastHeartbeatAt: LocalDateTime? = null
    var heartbeatMissingBeyondThreshold: Boolean? = null
    var heartbeatObservedAfterOffline: Boolean? = null
    var resourcePressureDetected: Boolean? = null
    var telemetryRetentionPolicy: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
