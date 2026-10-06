package tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.medo.shared.application.sync.SyncReadModelTarget
import tech.medo.shared.application.sync.SyncValueConverters

@Configuration
class AgentDictionaryValueTranslationCatalogReadModelSyncRegistration {
    @Bean
    fun agentDictionaryValueTranslationCatalogReadModelSyncTarget(repository: AgentDictionaryValueTranslationCatalogReadModelRepository): SyncReadModelTarget {
        val targetName = "AgentDictionaryValueTranslationCatalog"
        return SyncReadModelTarget(
            name = targetName,
            source = "DictionaryMaintenance.DictionaryValueTranslationCatalog",
            sourceContext = "DictionaryMaintenance",
            sourceReadModel = "DictionaryValueTranslationCatalog",
            sourcePath = "/sync/read-models/dictionary-maintenance/dictionary-value-translation-catalog",
            fieldMappings = mapOf(
            "dictionaryValueTranslationId" to "dictionaryValueTranslationId",
            "dictionaryValueId" to "dictionaryValueId",
            "dictionaryCode" to "dictionaryCode",
            "valueCode" to "valueCode",
            "locale" to "locale",
            "displayName" to "displayName",
            "description" to "description"
            ),
            queryParameters = { context ->
                mapOf(

                )
            },
            upsert = { row, syncedAt ->
                val id = SyncValueConverters.required(SyncValueConverters.uuid(row["dictionaryValueTranslationId"]), targetName, "dictionaryValueTranslationId")
                val projection = repository.findProjectionById(id) ?: AgentDictionaryValueTranslationCatalogReadModelProjection()
                projection.dictionaryValueTranslationId = id
                projection.dictionaryValueId = SyncValueConverters.required(SyncValueConverters.uuid(row["dictionaryValueId"]), targetName, "dictionaryValueId")
                projection.dictionaryCode = SyncValueConverters.required(SyncValueConverters.string(row["dictionaryCode"]), targetName, "dictionaryCode")
                projection.valueCode = SyncValueConverters.required(SyncValueConverters.string(row["valueCode"]), targetName, "valueCode")
                projection.locale = SyncValueConverters.required(SyncValueConverters.string(row["locale"]), targetName, "locale")
                projection.displayName = SyncValueConverters.required(SyncValueConverters.string(row["displayName"]), targetName, "displayName")
                projection.description = SyncValueConverters.string(row["description"])
                projection.syncedAt = syncedAt
                repository.save(projection)
            }
        )
    }
}
