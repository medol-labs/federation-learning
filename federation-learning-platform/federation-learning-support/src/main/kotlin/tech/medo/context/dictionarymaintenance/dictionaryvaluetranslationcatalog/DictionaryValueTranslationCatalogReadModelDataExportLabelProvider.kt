package tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.dictionaryvaluecatalogreadmodel.DictionaryValueCatalogReadModel
import tech.medo.dictionarymaintenance.dictionaryvaluecatalogreadmodel.DictionaryValueCatalogReadModelRepository
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModel
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModelRepository
import tech.medo.shared.application.export.DataExportDictionaryLabelProvider

@Component
class DictionaryValueTranslationCatalogReadModelDataExportLabelProvider(
    private val dictionaryValues: DictionaryValueCatalogReadModelRepository,
    private val dictionaryTranslations: DictionaryValueTranslationCatalogReadModelRepository
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

    private fun loadDictionaryValues(dictionaryCode: String): List<DictionaryValueCatalogReadModel> =
        loadAll { pageable -> dictionaryValues.findAllByFilter(dictionaryCode, true, null, pageable) }

    private fun loadDictionaryTranslations(dictionaryCode: String, locale: String): List<DictionaryValueTranslationCatalogReadModel> =
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
