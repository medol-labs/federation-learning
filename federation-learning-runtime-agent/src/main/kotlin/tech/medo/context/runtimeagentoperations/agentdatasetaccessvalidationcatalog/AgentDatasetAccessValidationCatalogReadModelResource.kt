package tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/agentdatasetaccessvalidation/agentdatasetaccessvalidationcatalog")
class AgentDatasetAccessValidationCatalogReadModelResource(private val repository: AgentDatasetAccessValidationCatalogReadModelRepository) {
    @GetMapping
    fun findAll(
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<AgentDatasetAccessValidationCatalogReadModel> =
        repository.findAll(pageable)


    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<AgentDatasetAccessValidationCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
