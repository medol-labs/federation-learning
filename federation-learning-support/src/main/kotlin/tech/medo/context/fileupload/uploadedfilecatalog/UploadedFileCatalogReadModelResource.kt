package tech.medo.fileupload.uploadedfilecatalog

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
@RequestMapping("/uploadedfile/uploadedfilecatalog")
class UploadedFileCatalogReadModelResource(private val repository: UploadedFileCatalogReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('uploaded_file_catalog:list') or hasAuthority('uploaded_file_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: UploadedFileCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<UploadedFileCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('uploaded_file_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<UploadedFileCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
