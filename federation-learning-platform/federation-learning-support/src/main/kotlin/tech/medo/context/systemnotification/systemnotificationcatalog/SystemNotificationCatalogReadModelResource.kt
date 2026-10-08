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
import java.util.UUID
import tech.medo.systemnotification.domain.types.NotificationCategory
import tech.medo.systemnotification.domain.types.NotificationSeverity
import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum

@CrossOrigin
@RestController
@RequestMapping("/systemnotification/systemnotificationcatalog")
class SystemNotificationCatalogReadModelResource(
    private val repository: SystemNotificationCatalogReadModelRepository
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('system_notification_catalog:list') or hasAuthority('system_notification_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: SystemNotificationCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<SystemNotificationCatalogReadModel> =
        findPage(criteria, pageable)


    private fun findPage(criteria: SystemNotificationCatalogReadModelCriteria, pageable: Pageable): Page<SystemNotificationCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)



    @PreAuthorize("hasAuthority('*:*') or hasAuthority('system_notification_catalog:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<SystemNotificationCatalogReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}
