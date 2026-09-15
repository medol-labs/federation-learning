package tech.medo.runtimeagentoperations.agentfeatureschemacatalog

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


@CrossOrigin
@RestController
@RequestMapping("/agentfeatureschemacatalog/agentfeatureschemacatalog")
class AgentFeatureSchemaCatalogReadModelResource(private val repository: AgentFeatureSchemaCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_feature_schema_catalog:list') or hasAuthority('agent_feature_schema_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: AgentFeatureSchemaCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<AgentFeatureSchemaCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('agent_feature_schema_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<AgentFeatureSchemaCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
