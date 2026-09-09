package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimenoderesourcelatestreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "runtime_node_resource_latest")
class RuntimeNodeResourceLatestReadModelEntity : MetadataProjection {
    @Id
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeNodeName: String? = null
    var nodeReady: Boolean? = null
    var allocatableCpuCores: Int? = null
    var allocatableMemoryGb: Int? = null
    var allocatableGpuCount: Int? = null
    var allocatedCpuCores: Int? = null
    var allocatedMemoryGb: Int? = null
    var allocatedGpuCount: Int? = null
    var availableCpuCores: Int? = null
    var availableMemoryGb: Int? = null
    var availableGpuCount: Int? = null
    var runningWorkloadCount: Int? = null
    var workloadCapacity: Int? = null
    var observedAt: LocalDateTime? = null
    var allocatableCapacityChanged: Boolean? = null
    var telemetryRetentionPolicy: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
