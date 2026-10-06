package tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@CrossOrigin
@RestController
@RequestMapping("/dictionaryvaluetranslation/dictionaryvaluetranslationcatalog")
class DictionaryValueTranslationCatalogReadModelResource(
    private val repository: DictionaryValueTranslationCatalogReadModelRepository
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('dictionary_value_translation_catalog:list') or hasAuthority('dictionary_value_translation_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: DictionaryValueTranslationCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<DictionaryValueTranslationCatalogReadModel> =
        findPage(criteria, pageable)


    private fun findPage(criteria: DictionaryValueTranslationCatalogReadModelCriteria, pageable: Pageable): Page<DictionaryValueTranslationCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)



    @PreAuthorize("hasAuthority('*:*') or hasAuthority('dictionary_value_translation_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<DictionaryValueTranslationCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
