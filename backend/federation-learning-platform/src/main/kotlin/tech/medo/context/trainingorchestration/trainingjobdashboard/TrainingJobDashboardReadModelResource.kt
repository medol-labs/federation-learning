package tech.medo.trainingorchestration.trainingjobdashboard

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
@RequestMapping("/trainingjob/trainingjobdashboard")
class TrainingJobDashboardReadModelResource(private val repository: TrainingJobDashboardReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('training_job_dashboard:list') or hasAuthority('training_job_dashboard:read')")
    @GetMapping
    fun findAll(
        criteria: TrainingJobDashboardReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<TrainingJobDashboardReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('training_job_dashboard:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<TrainingJobDashboardReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
