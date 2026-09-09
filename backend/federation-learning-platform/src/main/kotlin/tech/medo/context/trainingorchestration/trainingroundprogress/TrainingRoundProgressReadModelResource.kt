package tech.medo.trainingorchestration.trainingroundprogress

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
@RequestMapping("/traininground/trainingroundprogress")
class TrainingRoundProgressReadModelResource(private val repository: TrainingRoundProgressReadModelRepository) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('training_round_progress:list') or hasAuthority('training_round_progress:read')")
    @GetMapping
    fun findAll(
        criteria: TrainingRoundProgressReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<TrainingRoundProgressReadModel> =
        repository.findAllByCriteria(criteria, pageable)


}
