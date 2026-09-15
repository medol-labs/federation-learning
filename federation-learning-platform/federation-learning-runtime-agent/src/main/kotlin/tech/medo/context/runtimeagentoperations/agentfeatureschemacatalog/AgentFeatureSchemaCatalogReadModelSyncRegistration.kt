package tech.medo.runtimeagentoperations.agentfeatureschemacatalog

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.medo.shared.application.sync.SyncReadModelTarget
import tech.medo.shared.application.sync.SyncValueConverters

@Configuration
class AgentFeatureSchemaCatalogReadModelSyncRegistration {
    @Bean
    fun agentFeatureSchemaCatalogReadModelSyncTarget(repository: AgentFeatureSchemaCatalogReadModelRepository): SyncReadModelTarget {
        val targetName = "AgentFeatureSchemaCatalog"
        return SyncReadModelTarget(
            name = targetName,
            source = "DatasetGovernance.FeatureSchemaCatalog",
            sourceContext = "DatasetGovernance",
            sourceReadModel = "FeatureSchemaCatalog",
            sourcePath = "/sync/read-models/dataset-governance/feature-schema-catalog",
            fieldMappings = mapOf(
            "featureSchemaId" to "featureSchemaId",
            "featureDomain" to "featureDomain",
            "featureSchemaVersion" to "version",
            "schemaStatus" to "schemaStatus"
            ),
            queryParameters = { context ->
                mapOf(

                )
            },
            upsert = { row, syncedAt ->
                val id = SyncValueConverters.required(SyncValueConverters.uuid(row["featureSchemaId"]), targetName, "featureSchemaId")
                val projection = repository.findProjectionById(id) ?: AgentFeatureSchemaCatalogReadModelProjection()
                projection.featureSchemaId = id
                projection.featureDomain = SyncValueConverters.required(SyncValueConverters.string(row["featureDomain"]), targetName, "featureDomain")
                projection.featureSchemaVersion = SyncValueConverters.required(SyncValueConverters.string(row["version"]), targetName, "featureSchemaVersion")
                projection.schemaStatus = SyncValueConverters.required(SyncValueConverters.string(row["schemaStatus"]), targetName, "schemaStatus")
                projection.syncedAt = syncedAt
                repository.save(projection)
            }
        )
    }
}
