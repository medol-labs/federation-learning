package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimeinfrastructureconnectioncatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
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
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
