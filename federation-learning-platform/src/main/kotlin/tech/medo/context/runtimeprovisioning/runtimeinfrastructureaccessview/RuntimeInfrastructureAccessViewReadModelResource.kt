package tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview

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
@RequestMapping("/runtimeinfrastructure/runtimeinfrastructureaccessview")
class RuntimeInfrastructureAccessViewReadModelResource(private val repository: RuntimeInfrastructureAccessViewReadModelRepository) {
    @GetMapping
    fun findAll(
        criteria: RuntimeInfrastructureAccessViewReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeInfrastructureAccessViewReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeInfrastructureAccessViewReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
