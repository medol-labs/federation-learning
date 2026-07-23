package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimehealthdashboardreadmodel

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
class RuntimeHealthDashboardReadModelEntity : MetadataProjection {
    @Id
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var federationId: UUID? = null
    var trainingJobId: UUID? = null
    var roundExecutionId: UUID? = null
    var federationName: String? = null
    var trainingJobObjective: String? = null
    var cpuLoad: BigDecimal? = null
    var gpuLoad: BigDecimal? = null
    var memoryLoad: BigDecimal? = null
    var nodeReady: Boolean? = null
    var availableCpuCores: Int? = null
    var availableMemoryGb: Int? = null
    var availableGpuCount: Int? = null
    var runningWorkloadCount: Int? = null
    var workloadCapacity: Int? = null
    var healthStatus: String? = null
    var lastHeartbeatAt: LocalDateTime? = null
    var lastResourceSnapshotAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
