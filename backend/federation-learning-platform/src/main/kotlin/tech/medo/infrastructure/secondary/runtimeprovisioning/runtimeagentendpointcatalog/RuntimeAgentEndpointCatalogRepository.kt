package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeagentendpointcatalog

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RuntimeAgentEndpointCatalogRepository : JpaRepository<RuntimeAgentEndpointCatalogEntity, UUID> {
    fun findByRuntimeAgentId(runtimeAgentId: UUID): RuntimeAgentEndpointCatalogEntity?
    fun findByRuntimeInfrastructureId(runtimeInfrastructureId: UUID): RuntimeAgentEndpointCatalogEntity?
    fun findFirstByRuntimeIdAndConnectionStatusOrderByConnectedAtDesc(
        runtimeId: UUID,
        connectionStatus: String
    ): RuntimeAgentEndpointCatalogEntity?
}
