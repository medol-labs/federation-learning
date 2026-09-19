package tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.medo.shared.application.sync.SyncReadModelTarget
import tech.medo.shared.application.sync.SyncValueConverters

@Configuration
class AgentDictionaryValueCatalogReadModelSyncRegistration {
    @Bean
    fun agentDictionaryValueCatalogReadModelSyncTarget(repository: AgentDictionaryValueCatalogReadModelRepository): SyncReadModelTarget {
        val targetName = "AgentDictionaryValueCatalog"
        return SyncReadModelTarget(
            name = targetName,
            source = "DictionaryMaintenance.DictionaryValueCatalog",
            sourceContext = "DictionaryMaintenance",
            sourceReadModel = "DictionaryValueCatalog",
            sourcePath = "/sync/read-models/dictionary-maintenance/dictionary-value-catalog",
            fieldMappings = mapOf(
            "dictionaryValueId" to "dictionaryValueId",
            "dictionaryId" to "dictionaryId",
            "dictionaryCode" to "dictionaryCode",
            "valueCode" to "valueCode",
            "displayName" to "displayName",
            "displayOrder" to "displayOrder",
            "active" to "active",
            "state" to "state"
            ),
            queryParameters = { context ->
                mapOf(

                )
            },
            upsert = { row, syncedAt ->
                val id = SyncValueConverters.required(SyncValueConverters.uuid(row["dictionaryValueId"]), targetName, "dictionaryValueId")
                val projection = repository.findProjectionById(id) ?: AgentDictionaryValueCatalogReadModelProjection()
                projection.dictionaryValueId = id
                projection.dictionaryId = SyncValueConverters.required(SyncValueConverters.uuid(row["dictionaryId"]), targetName, "dictionaryId")
                projection.dictionaryCode = SyncValueConverters.required(SyncValueConverters.string(row["dictionaryCode"]), targetName, "dictionaryCode")
                projection.valueCode = SyncValueConverters.required(SyncValueConverters.string(row["valueCode"]), targetName, "valueCode")
                projection.displayName = SyncValueConverters.required(SyncValueConverters.string(row["displayName"]), targetName, "displayName")
                projection.displayOrder = SyncValueConverters.int(row["displayOrder"])
                projection.active = SyncValueConverters.required(SyncValueConverters.boolean(row["active"]), targetName, "active")
                projection.state = SyncValueConverters.required(SyncValueConverters.string(row["state"]), targetName, "state")
                projection.syncedAt = syncedAt
                repository.save(projection)
            }
        )
    }
}
