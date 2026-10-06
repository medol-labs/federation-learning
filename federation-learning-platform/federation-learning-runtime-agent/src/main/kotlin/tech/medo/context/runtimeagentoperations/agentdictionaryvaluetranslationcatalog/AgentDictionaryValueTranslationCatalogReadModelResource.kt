package tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog

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
@RequestMapping("/agentdictionaryvaluetranslationcatalog/agentdictionaryvaluetranslationcatalog")
class AgentDictionaryValueTranslationCatalogReadModelResource(
    private val repository: AgentDictionaryValueTranslationCatalogReadModelRepository
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_dictionary_value_translation_catalog:list') or hasAuthority('agent_dictionary_value_translation_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: AgentDictionaryValueTranslationCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<AgentDictionaryValueTranslationCatalogReadModel> =
        findPage(criteria, pageable)


    private fun findPage(criteria: AgentDictionaryValueTranslationCatalogReadModelCriteria, pageable: Pageable): Page<AgentDictionaryValueTranslationCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)



    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_dictionary_value_translation_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<AgentDictionaryValueTranslationCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
