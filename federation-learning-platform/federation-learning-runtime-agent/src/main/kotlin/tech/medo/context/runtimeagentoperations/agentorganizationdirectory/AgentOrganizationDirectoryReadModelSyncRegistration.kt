package tech.medo.runtimeagentoperations.agentorganizationdirectory

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.medo.shared.application.sync.SyncReadModelTarget
import tech.medo.shared.application.sync.SyncValueConverters

@Configuration
class AgentOrganizationDirectoryReadModelSyncRegistration {
    @Bean
    fun agentOrganizationDirectoryReadModelSyncTarget(repository: AgentOrganizationDirectoryReadModelRepository): SyncReadModelTarget {
        val targetName = "AgentOrganizationDirectory"
        return SyncReadModelTarget(
            name = targetName,
            source = "OrganizationManagement.OrganizationDirectory",
            sourceContext = "OrganizationManagement",
            sourceReadModel = "OrganizationDirectory",
            sourcePath = "/sync/read-models/organization-management/organization-directory",
            fieldMappings = mapOf(
            "organizationId" to "organizationId",
            "organizationName" to "organizationName",
            "organizationType" to "organizationType",
            "state" to "state"
            ),
            queryParameters = { context ->
                mapOf(
                    "organizationId" to context.requiredParameter("organizationId", targetName)
                )
            },
            upsert = { row, syncedAt ->
                val id = SyncValueConverters.required(SyncValueConverters.uuid(row["organizationId"]), targetName, "organizationId")
                val projection = repository.findProjectionById(id) ?: AgentOrganizationDirectoryReadModelProjection()
                projection.organizationId = id
                projection.organizationName = SyncValueConverters.required(SyncValueConverters.string(row["organizationName"]), targetName, "organizationName")
                projection.organizationType = SyncValueConverters.required(SyncValueConverters.string(row["organizationType"]), targetName, "organizationType")
                projection.state = SyncValueConverters.required(SyncValueConverters.string(row["state"]), targetName, "state")
                projection.syncedAt = syncedAt
                repository.save(projection)
            }
        )
    }
}
