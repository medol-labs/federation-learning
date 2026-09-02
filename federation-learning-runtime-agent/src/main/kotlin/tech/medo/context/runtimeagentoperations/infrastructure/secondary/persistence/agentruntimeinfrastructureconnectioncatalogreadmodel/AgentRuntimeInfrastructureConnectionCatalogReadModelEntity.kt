package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimeinfrastructureconnectioncatalogreadmodel

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
@Table(name = "agent_runtime_infrastructure_connection_catalog")
class AgentRuntimeInfrastructureConnectionCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var runtimePlatformConnectionReady: Boolean? = null
    var platformApiReachable: Boolean? = null
    var agentAuthenticationSucceeded: Boolean? = null
    var controlChannelEstablished: Boolean? = null
    var heartbeatAccepted: Boolean? = null
    var connectedAt: LocalDateTime? = null
    var connectionReportFailedAt: LocalDateTime? = null
    @Column(columnDefinition = "text")
    var connectionReportFailureReason: String? = null
    var connectionReportRetryable: Boolean? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
