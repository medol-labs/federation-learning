package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeagentendpointcatalog

import org.springframework.stereotype.Component
import java.util.UUID

interface RuntimeAgentEndpointResolver {
    fun resolveConnectedEndpoint(runtimeId: UUID): String?
}

@Component
class JpaRuntimeAgentEndpointResolver(
    private val repository: RuntimeAgentEndpointCatalogRepository
) : RuntimeAgentEndpointResolver {
    override fun resolveConnectedEndpoint(runtimeId: UUID): String? =
        repository
            .findFirstByRuntimeIdAndConnectionStatusOrderByConnectedAtDesc(runtimeId, "CONNECTED")
            ?.runtimeAgentEndpoint
            ?.trim()
            ?.takeIf { it.isNotBlank() }
}
