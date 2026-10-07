package tech.medo.identityaccessmanagement.userroleassignmentcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import tech.jhipster.service.filter.RangeFilter
import tech.medo.shared.application.export.DataExportColumn
import tech.medo.shared.application.export.DataExportExecutionResult
import tech.medo.shared.application.export.DataExportExecutionTask
import tech.medo.shared.application.export.DataExportRequest
import tech.medo.shared.application.export.DataExportResourceExecutor
import tech.medo.shared.application.export.DataExportService
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@CrossOrigin
@RestController
@RequestMapping("/userroleassignment/userroleassignmentcatalog")
class UserRoleAssignmentCatalogReadModelResource(
    private val repository: UserRoleAssignmentCatalogReadModelRepository,
    private val dataExportService: DataExportService
) {
    private val exportColumns = listOf(
        DataExportColumn("userAccountId", "userAccountId"),
        DataExportColumn("username", "username"),
        DataExportColumn("roleCode", "roleCode"),
        DataExportColumn("roleName", "roleName")
    )

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('user_role_assignment_catalog:list') or hasAuthority('user_role_assignment_catalog:read')")
    @GetMapping
    fun findAll(
        criteria: UserRoleAssignmentCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<UserRoleAssignmentCatalogReadModel> =
        findPage(criteria, pageable)

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('user_role_assignment_catalog:export') or hasAuthority('user_role_assignment_catalog:list') or hasAuthority('user_role_assignment_catalog:read')")
    @PostMapping("/export")
    fun export(
        @RequestBody(required = false) request: DataExportRequest?,
        criteria: UserRoleAssignmentCatalogReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Any> {
        val columns = dataExportService.validatedColumns(request?.columns, exportColumns)
        val snapshotUpperBound = LocalDateTime.now(ZoneOffset.UTC)
        val snapshotCriteria = applyExportSnapshot(criteria, snapshotUpperBound)
        val exportPageable = PageRequest.of(0, dataExportService.pageSize(), pageable.sort)
        val firstPage = findPage(snapshotCriteria, exportPageable)
        return dataExportService.export("userroleassignmentcatalog", columns, snapshotCriteria, pageable.sort, firstPage, fetchPage = { nextPage ->
            findPage(snapshotCriteria, nextPage)
        }, requestedLocale = request?.requestedLocale, snapshotUpperBound = snapshotUpperBound.toInstant(ZoneOffset.UTC))
    }


    private fun findPage(criteria: UserRoleAssignmentCatalogReadModelCriteria, pageable: Pageable): Page<UserRoleAssignmentCatalogReadModel> =
        repository.findAllByCriteria(criteria, pageable)

    private fun applyExportSnapshot(criteria: UserRoleAssignmentCatalogReadModelCriteria, snapshotUpperBound: LocalDateTime): UserRoleAssignmentCatalogReadModelCriteria {
        val projectionUpdatedAt = criteria.projectionUpdatedAt ?: RangeFilter<LocalDateTime>().also {
            criteria.projectionUpdatedAt = it
        }
        val requestedUpperBound = projectionUpdatedAt.getLessThanOrEqual()
        if (requestedUpperBound == null || requestedUpperBound.isAfter(snapshotUpperBound)) {
            projectionUpdatedAt.setLessThanOrEqual(snapshotUpperBound)
        }
        return criteria
    }



}

@Component
class UserRoleAssignmentCatalogReadModelDataExportExecutor(
    private val repository: UserRoleAssignmentCatalogReadModelRepository,
    private val dataExportService: DataExportService,
    private val objectMapper: ObjectMapper
) : DataExportResourceExecutor {
    override val resourceName: String = "userroleassignmentcatalog"
    private val exportColumns = listOf(
        DataExportColumn("userAccountId", "userAccountId"),
        DataExportColumn("username", "username"),
        DataExportColumn("roleCode", "roleCode"),
        DataExportColumn("roleName", "roleName")
    )

    override fun execute(task: DataExportExecutionTask): DataExportExecutionResult {
        val criteria = objectMapper.readValue<UserRoleAssignmentCatalogReadModelCriteria>(task.criteriaJson)
        val snapshotUpperBound = LocalDateTime.ofInstant(task.snapshotUpperBound, ZoneOffset.UTC)
        applyExportSnapshot(criteria, snapshotUpperBound)
        val columns = dataExportService.columnsFromJson(task.columnsJson, exportColumns)
        val sort = dataExportService.sortFromJson(task.sortJson)
        val firstPage = repository.findAllByCriteria(criteria, PageRequest.of(0, dataExportService.pageSize(), sort))
        return dataExportService.writeCsvFile(task.fileName, columns, firstPage, requestedLocale = task.requestedLocale) { nextPage ->
            repository.findAllByCriteria(criteria, nextPage)
        }
    }

    private fun applyExportSnapshot(criteria: UserRoleAssignmentCatalogReadModelCriteria, snapshotUpperBound: LocalDateTime): UserRoleAssignmentCatalogReadModelCriteria {
        val projectionUpdatedAt = criteria.projectionUpdatedAt ?: RangeFilter<LocalDateTime>().also {
            criteria.projectionUpdatedAt = it
        }
        val requestedUpperBound = projectionUpdatedAt.getLessThanOrEqual()
        if (requestedUpperBound == null || requestedUpperBound.isAfter(snapshotUpperBound)) {
            projectionUpdatedAt.setLessThanOrEqual(snapshotUpperBound)
        }
        return criteria
    }
}
