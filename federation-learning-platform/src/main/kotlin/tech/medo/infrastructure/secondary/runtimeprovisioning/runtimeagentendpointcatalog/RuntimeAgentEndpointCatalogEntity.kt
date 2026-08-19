package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeagentendpointcatalog

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.UUID

@Entity
class RuntimeAgentEndpointCatalogEntity {
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
}
