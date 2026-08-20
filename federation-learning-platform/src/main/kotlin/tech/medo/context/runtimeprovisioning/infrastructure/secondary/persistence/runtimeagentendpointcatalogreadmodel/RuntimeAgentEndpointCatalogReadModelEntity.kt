package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeagentendpointcatalogreadmodel

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
class RuntimeAgentEndpointCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeAgentId: UUID? = null
    var runtimeId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var organizationId: UUID? = null
    var runtimeName: String? = null
    var runtimeAgentEndpoint: String? = null
    var endpointScope: String? = null
    var connectionStatus: String? = null
    var connectedAt: LocalDateTime? = null
    var activatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
