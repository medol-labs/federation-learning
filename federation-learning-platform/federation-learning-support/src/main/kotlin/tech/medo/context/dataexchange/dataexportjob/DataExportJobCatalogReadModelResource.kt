package tech.medo.dataexchange.dataexportjob

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
@RequestMapping("/dataexportjob/dataexportjobcatalog")
class DataExportJobCatalogReadModelResource(
    private val repository: DataExportJobCatalogReadModelRepository
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('data_export_job_catalog:list') or hasAuthority('data_export_job_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: DataExportJobCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<DataExportJobCatalogReadModel> =
        findPage(criteria, pageable)


    private fun findPage(criteria: DataExportJobCatalogReadModelCriteria, pageable: Pageable): Page<DataExportJobCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)



    @PreAuthorize("hasAuthority('*:*') or hasAuthority('data_export_job_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<DataExportJobCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
