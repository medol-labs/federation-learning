package tech.medo.datasetgovernance.featureschemacatalog

import org.springframework.data.domain.PageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/sync/read-models/dataset-governance/feature-schema-catalog")
class FeatureSchemaCatalogReadModelSyncReadModelResource(private val repository: FeatureSchemaCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('feature_schema_catalog:list') or hasAuthority('feature_schema_catalog:read')")
    @GetMapping
    fun findAllForSync(
        @RequestParam(defaultValue = "200") size: Int
    ): Map<String, Any?> {
        val page = repository.findAll(PageRequest.of(0, size.coerceIn(1, 1000)))
        return mapOf(
            "items" to page.content,
            "nextCursor" to null
        )
    }
}
