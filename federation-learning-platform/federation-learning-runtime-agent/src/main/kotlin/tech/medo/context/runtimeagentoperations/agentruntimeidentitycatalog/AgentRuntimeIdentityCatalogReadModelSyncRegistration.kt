package tech.medo.runtimeagentoperations.agentruntimeidentitycatalog

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.medo.shared.application.sync.SyncReadModelTarget
import tech.medo.shared.application.sync.SyncValueConverters

@Configuration
class AgentRuntimeIdentityCatalogReadModelSyncRegistration {
    @Bean
    fun agentRuntimeIdentityCatalogReadModelSyncTarget(repository: AgentRuntimeIdentityCatalogReadModelRepository): SyncReadModelTarget {
        val targetName = "AgentRuntimeIdentityCatalog"
        return SyncReadModelTarget(
            name = targetName,
            source = "RuntimeProvisioning.RuntimeIdentityCatalog",
            sourceContext = "RuntimeProvisioning",
            sourceReadModel = "RuntimeIdentityCatalog",
            sourcePath = "/sync/read-models/runtime-provisioning/runtime-identity-catalog",
            fieldMappings = mapOf(

            ),
            queryParameters = { context ->
                mapOf(
                    "runtimeAgentId" to context.requiredParameter("runtimeAgentId", targetName)
                )
            },
            upsert = { row, syncedAt ->
                val id = SyncValueConverters.required(SyncValueConverters.uuid(row["undefined"]), targetName, "runtimeId")
                val projection = repository.findProjectionById(id) ?: AgentRuntimeIdentityCatalogReadModelProjection()
                projection.runtimeId = id
                projection.syncedAt = syncedAt
                repository.save(projection)
            }
        )
    }
}
