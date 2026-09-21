package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeagentendpointcatalog

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import tech.jhipster.service.filter.StringFilter
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelCriteria
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelRepository
import java.util.UUID

interface RuntimeAgentEndpointResolver {
    fun resolveConnectedEndpoint(runtimeId: UUID): String?
}

@Component
class JpaRuntimeAgentEndpointResolver(
    private val repository: RuntimeAgentEndpointCatalogRepository,
    private val runtimeIdentityRepository: RuntimeIdentityCatalogReadModelRepository
) : RuntimeAgentEndpointResolver {
    override fun resolveConnectedEndpoint(runtimeId: UUID): String? =
        repository
            .findFirstByRuntimeIdAndConnectionStatusOrderByConnectedAtDesc(runtimeId, "CONNECTED")
            ?.connectedEndpoint()
            ?: resolveByRuntimeIdentity(runtimeId)

    private fun resolveByRuntimeIdentity(runtimeId: UUID): String? {
        val identity = runtimeIdentityRepository.findAllByCriteria(
            RuntimeIdentityCatalogReadModelCriteria().apply {
                this.runtimeId = StringFilter().apply { equals = runtimeId.toString() }
            },
            PageRequest.of(0, 1)
        ).content.firstOrNull()

        val runtimeAgentId = identity?.runtimeAgentId ?: return null
        return repository
            .findByRuntimeAgentId(runtimeAgentId)
            ?.takeIf { it.connectionStatus == "CONNECTED" }
            ?.connectedEndpoint()
    }

    private fun RuntimeAgentEndpointCatalogEntity.connectedEndpoint(): String? =
        runtimeAgentEndpoint
            ?.trim()
            ?.takeIf { it.isNotBlank() }
}
