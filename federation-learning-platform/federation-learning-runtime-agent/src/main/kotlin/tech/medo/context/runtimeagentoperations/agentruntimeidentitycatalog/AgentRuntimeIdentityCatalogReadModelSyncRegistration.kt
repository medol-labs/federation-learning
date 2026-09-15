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
            "runtimeId" to "runtimeId",
            "runtimeInfrastructureId" to "runtimeInfrastructureId",
            "runtimeAgentId" to "runtimeAgentId",
            "organizationId" to "organizationId",
            "organizationName" to "organizationName",
            "runtimeName" to "runtimeName",
            "identityStatus" to "identityStatus",
            "activatedAt" to "activatedAt",
            "revokedAt" to "revokedAt"
            ),
            queryParameters = { context ->
                mapOf(
                    "runtimeAgentId" to context.requiredParameter("runtimeAgentId", targetName)
                )
            },
            upsert = { row, syncedAt ->
                val id = SyncValueConverters.required(SyncValueConverters.uuid(row["runtimeId"]), targetName, "runtimeId")
                val projection = repository.findProjectionById(id) ?: AgentRuntimeIdentityCatalogReadModelProjection()
                projection.runtimeId = id
                projection.runtimeInfrastructureId = SyncValueConverters.required(SyncValueConverters.uuid(row["runtimeInfrastructureId"]), targetName, "runtimeInfrastructureId")
                projection.runtimeAgentId = SyncValueConverters.required(SyncValueConverters.uuid(row["runtimeAgentId"]), targetName, "runtimeAgentId")
                projection.organizationId = SyncValueConverters.required(SyncValueConverters.uuid(row["organizationId"]), targetName, "organizationId")
                projection.organizationName = SyncValueConverters.string(row["organizationName"])
                projection.runtimeName = SyncValueConverters.required(SyncValueConverters.string(row["runtimeName"]), targetName, "runtimeName")
                projection.identityStatus = SyncValueConverters.required(SyncValueConverters.string(row["identityStatus"]), targetName, "identityStatus")
                projection.activatedAt = SyncValueConverters.localDateTime(row["activatedAt"])
                projection.revokedAt = SyncValueConverters.localDateTime(row["revokedAt"])
                projection.syncedAt = syncedAt
                repository.save(projection)
            }
        )
    }
}
