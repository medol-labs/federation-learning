package tech.medo.trainingorchestration.trainingparticipanteligibility

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
@RequestMapping("/trainingjob/trainingparticipanteligibility")
class TrainingParticipantEligibilityReadModelResource(private val repository: TrainingParticipantEligibilityReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('training_participant_eligibility:list') or hasAuthority('training_participant_eligibility:read')")
    @GetMapping
    fun findAll(
        criteria: TrainingParticipantEligibilityReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<TrainingParticipantEligibilityReadModel> =
        repository.findAllByCriteria(criteria, pageable)


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('training_participant_eligibility:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<TrainingParticipantEligibilityReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
