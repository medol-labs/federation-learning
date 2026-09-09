package tech.medo.dictionarymaintenance.dictionaryvaluecatalog

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
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum;


@CrossOrigin
@RestController
@RequestMapping("/dictionaryvalue/dictionaryvaluecatalog")
class DictionaryValueCatalogReadModelResource(private val repository: DictionaryValueCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('dictionary_value_catalog:list') or hasAuthority('dictionary_value_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: DictionaryValueCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<DictionaryValueCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('dictionary_value_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<DictionaryValueCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
