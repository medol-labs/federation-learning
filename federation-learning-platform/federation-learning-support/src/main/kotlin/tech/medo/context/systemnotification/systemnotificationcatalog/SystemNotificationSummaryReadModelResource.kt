package tech.medo.systemnotification.systemnotificationcatalog

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


@CrossOrigin
@RestController
@RequestMapping("/systemnotification/systemnotificationsummary")
class SystemNotificationSummaryReadModelResource(
    private val repository: SystemNotificationSummaryReadModelRepository
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('system_notification_summary:list') or hasAuthority('system_notification_summary:read')")
    @GetMapping
    fun findAll(
        criteria: SystemNotificationSummaryReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<SystemNotificationSummaryReadModel> =
        findPage(criteria, pageable)


    private fun findPage(criteria: SystemNotificationSummaryReadModelCriteria, pageable: Pageable): Page<SystemNotificationSummaryReadModel> =
        repository.findAllByCriteria(criteria, pageable)



    @PreAuthorize("hasAuthority('*:*') or hasAuthority('system_notification_summary:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): ResponseEntity<SystemNotificationSummaryReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
