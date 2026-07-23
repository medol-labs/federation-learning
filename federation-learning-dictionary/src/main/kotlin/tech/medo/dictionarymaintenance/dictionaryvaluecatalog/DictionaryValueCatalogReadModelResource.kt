package tech.medo.dictionarymaintenance.dictionaryvaluecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum;


@CrossOrigin
@RestController
@RequestMapping("/dictionaryvalue/dictionaryvaluecatalog")
class DictionaryValueCatalogReadModelResource(private val repository: DictionaryValueCatalogReadModelRepository) {
    @GetMapping
    fun findAll(
        @RequestParam(required = false) dictionaryCode: String?,
        @RequestParam(required = false) active: Boolean?,
        @RequestParam(required = false) state: DictionaryValueStateEnum?,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<DictionaryValueCatalogReadModel> =
        repository.findAllByFilter(dictionaryCode, active, state, pageable)


    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<DictionaryValueCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
