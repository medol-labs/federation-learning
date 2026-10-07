package tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModel
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModelRepository
import tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog.AgentDictionaryValueTranslationCatalogReadModel
import tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog.AgentDictionaryValueTranslationCatalogReadModelRepository
import tech.medo.shared.application.export.DataExportDictionaryLabelProvider

@Component
class AgentDictionaryValueTranslationCatalogReadModelDataExportLabelProvider(
    private val dictionaryValues: AgentDictionaryValueCatalogReadModelRepository,
    private val dictionaryTranslations: AgentDictionaryValueTranslationCatalogReadModelRepository
) : DataExportDictionaryLabelProvider {
    override fun labels(dictionaryCode: String, locale: String?): Map<String, String> {
        val labels = mutableMapOf<String, String>()
        loadDictionaryValues(dictionaryCode).forEach { value ->
            val valueCode = value.valueCode ?: return@forEach
            labels[valueCode] = value.defaultDisplayName ?: valueCode
        }
        fallbackLocales(locale).forEach { currentLocale ->
            loadDictionaryTranslations(dictionaryCode, currentLocale).forEach { translation ->
                val valueCode = translation.valueCode ?: return@forEach
                labels[valueCode] = translation.displayName ?: labels[valueCode] ?: valueCode
            }
        }
        return labels
    }

    private fun fallbackLocales(locale: String?): List<String> {
        val normalized = locale?.trim()?.takeIf { it.isNotEmpty() } ?: return emptyList()
        val base = normalized.substringBefore("-").substringBefore("_")
        return listOf(base, normalized).distinct()
    }

    private fun loadDictionaryValues(dictionaryCode: String): List<AgentDictionaryValueCatalogReadModel> =
        loadAll { pageable -> dictionaryValues.findAllByFilter(dictionaryCode, true, null, pageable) }

    private fun loadDictionaryTranslations(dictionaryCode: String, locale: String): List<AgentDictionaryValueTranslationCatalogReadModel> =
        loadAll { pageable -> dictionaryTranslations.findAllByFilter(dictionaryCode, null, locale, pageable) }

    private fun <T> loadAll(fetch: (Pageable) -> Page<T>): List<T> {
        val records = mutableListOf<T>()
        var pageNumber = 0
        do {
            val page = fetch(PageRequest.of(pageNumber, 1000))
            records += page.content
            pageNumber += 1
        } while (pageNumber < page.totalPages)
        return records
    }
}
