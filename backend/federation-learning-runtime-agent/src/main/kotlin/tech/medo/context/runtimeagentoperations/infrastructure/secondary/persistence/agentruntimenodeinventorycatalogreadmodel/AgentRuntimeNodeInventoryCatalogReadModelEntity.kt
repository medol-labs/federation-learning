package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimenodeinventorycatalogreadmodel

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
@Table(name = "agent_runtime_node_inventory_catalog")
class AgentRuntimeNodeInventoryCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeNodeInventoryReportId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var organizationName: String? = null
    var runtimeNodeName: String? = null
    var infrastructureNodeId: String? = null
    var runtimeNodeRole: String? = null
    var nodeReady: Boolean? = null
    var runtimeEngineVersion: String? = null
    var containerEngineVersion: String? = null
    var operatingSystem: String? = null
    var architecture: String? = null
    var inventoryHash: String? = null
    var discoveredAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
