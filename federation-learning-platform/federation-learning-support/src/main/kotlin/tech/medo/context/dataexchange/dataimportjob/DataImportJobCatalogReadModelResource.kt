package tech.medo.dataexchange.dataimportjob

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
@RequestMapping("/dataimportjob/dataimportjobcatalog")
class DataImportJobCatalogReadModelResource(
    private val repository: DataImportJobCatalogReadModelRepository
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('data_import_job_catalog:list') or hasAuthority('data_import_job_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: DataImportJobCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<DataImportJobCatalogReadModel> =
        findPage(criteria, pageable)


    private fun findPage(criteria: DataImportJobCatalogReadModelCriteria, pageable: Pageable): Page<DataImportJobCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)



    @PreAuthorize("hasAuthority('*:*') or hasAuthority('data_import_job_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<DataImportJobCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
