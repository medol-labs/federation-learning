package tech.medo.runtimemonitoring.runtimenodeinventoryview

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import com.fasterxml.jackson.databind.ObjectMapper
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
import tech.medo.shared.application.export.DataExportColumn
import tech.medo.shared.application.export.DataExportExecutionTask
import tech.medo.shared.application.export.DataExportResourceExecutor
import tech.medo.shared.application.export.DataExportRequest
import tech.medo.shared.application.export.DataExportService
import tech.jhipster.service.filter.RangeFilter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/runtimenodeinventory/runtimenodeinventoryview")
class RuntimeNodeInventoryViewReadModelResource(
    private val repository: RuntimeNodeInventoryViewReadModelRepository,
    private val dataExportService: DataExportService
) {
    private val exportColumns = listOf(
        DataExportColumn("nodeId", "nodeId"),
        DataExportColumn("runtimeNodeInventoryReportId", "runtimeNodeInventoryReportId"),
        DataExportColumn("organizationId", "organizationId"),
        DataExportColumn("runtimeInfrastructureId", "runtimeInfrastructureId"),
        DataExportColumn("runtimeAgentId", "runtimeAgentId"),
        DataExportColumn("organizationName", "organizationName"),
        DataExportColumn("runtimeName", "runtimeName"),
        DataExportColumn("runtimeNodeName", "runtimeNodeName"),
        DataExportColumn("infrastructureNodeId", "infrastructureNodeId"),
        DataExportColumn("runtimeNodeRole", "runtimeNodeRole"),
        DataExportColumn("nodeReady", "nodeReady"),
        DataExportColumn("runtimeEngineVersion", "runtimeEngineVersion"),
        DataExportColumn("containerEngineVersion", "containerEngineVersion"),
        DataExportColumn("operatingSystem", "operatingSystem"),
        DataExportColumn("architecture", "architecture"),
        DataExportColumn("inventoryHash", "inventoryHash"),
        DataExportColumn("discoveredAt", "discoveredAt"),
        DataExportColumn("recordedAt", "recordedAt")
    )

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_node_inventory_view:list') or hasAuthority('runtime_node_inventory_view:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeNodeInventoryViewReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeNodeInventoryViewReadModel> =
        findPage(criteria, pageable)

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_node_inventory_view:export') or hasAuthority('runtime_node_inventory_view:list') or hasAuthority('runtime_node_inventory_view:read')")
    @PostMapping("/export")
    fun export(
        @RequestBody(required = false) request: DataExportRequest?,
        criteria: RuntimeNodeInventoryViewReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Any> {
        val columns = dataExportService.validatedColumns(request?.columns, exportColumns)
        val snapshotUpperBound = LocalDateTime.now(ZoneOffset.UTC)
        val snapshotCriteria = applyExportSnapshot(criteria, snapshotUpperBound)
        val exportPageable = PageRequest.of(0, dataExportService.pageSize(), pageable.sort)
        val firstPage = findPage(snapshotCriteria, exportPageable)
        return dataExportService.export("runtimenodeinventoryview", columns, snapshotCriteria, pageable.sort, firstPage, fetchPage = { nextPage ->
            findPage(snapshotCriteria, nextPage)
        }, snapshotUpperBound = snapshotUpperBound.toInstant(ZoneOffset.UTC))
    }


    private fun findPage(criteria: RuntimeNodeInventoryViewReadModelCriteria, pageable: Pageable): Page<RuntimeNodeInventoryViewReadModel> =
        repository.findAllByCriteria(criteria, pageable)

    private fun applyExportSnapshot(criteria: RuntimeNodeInventoryViewReadModelCriteria, snapshotUpperBound: LocalDateTime): RuntimeNodeInventoryViewReadModelCriteria {
        val projectionUpdatedAt = criteria.projectionUpdatedAt ?: RangeFilter<LocalDateTime>().also {
            criteria.projectionUpdatedAt = it
        }
        val requestedUpperBound = projectionUpdatedAt.getLessThanOrEqual()
        if (requestedUpperBound == null || requestedUpperBound.isAfter(snapshotUpperBound)) {
            projectionUpdatedAt.setLessThanOrEqual(snapshotUpperBound)
        }
        return criteria
    }


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_node_inventory_view:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeNodeInventoryViewReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}

@Component
class RuntimeNodeInventoryViewDataExportExecutor(
    private val repository: RuntimeNodeInventoryViewReadModelRepository,
    private val dataExportService: DataExportService,
    private val objectMapper: ObjectMapper
) : DataExportResourceExecutor {
    override val resourceName: String = "runtimenodeinventoryview"

    override fun execute(task: DataExportExecutionTask) =
        dataExportService.writeCsvFile(
            fileName = task.fileName,
            columns = dataExportService.columnsFromJson(task.columnsJson, exportColumns),
            firstPage = firstPage(task),
            fetchPage = { nextPage -> repository.findAllByCriteria(criteria(task), nextPage) }
        )

    private fun firstPage(task: DataExportExecutionTask): Page<RuntimeNodeInventoryViewReadModel> {
        val pageable = PageRequest.of(0, dataExportService.pageSize(), dataExportService.sortFromJson(task.sortJson))
        return repository.findAllByCriteria(criteria(task), pageable)
    }

    private fun criteria(task: DataExportExecutionTask): RuntimeNodeInventoryViewReadModelCriteria {
        val criteria = objectMapper.readValue(task.criteriaJson, RuntimeNodeInventoryViewReadModelCriteria::class.java)
        val snapshotUpperBound = LocalDateTime.ofInstant(task.snapshotUpperBound, ZoneOffset.UTC)
        applyExportSnapshot(criteria, snapshotUpperBound)
        return criteria
    }

    private fun applyExportSnapshot(criteria: RuntimeNodeInventoryViewReadModelCriteria, snapshotUpperBound: LocalDateTime): RuntimeNodeInventoryViewReadModelCriteria {
        val projectionUpdatedAt = criteria.projectionUpdatedAt ?: RangeFilter<LocalDateTime>().also {
            criteria.projectionUpdatedAt = it
        }
        val requestedUpperBound = projectionUpdatedAt.getLessThanOrEqual()
        if (requestedUpperBound == null || requestedUpperBound.isAfter(snapshotUpperBound)) {
            projectionUpdatedAt.setLessThanOrEqual(snapshotUpperBound)
        }
        return criteria
    }

    private val exportColumns = listOf(
        DataExportColumn("nodeId", "nodeId"),
        DataExportColumn("runtimeNodeInventoryReportId", "runtimeNodeInventoryReportId"),
        DataExportColumn("organizationId", "organizationId"),
        DataExportColumn("runtimeInfrastructureId", "runtimeInfrastructureId"),
        DataExportColumn("runtimeAgentId", "runtimeAgentId"),
        DataExportColumn("organizationName", "organizationName"),
        DataExportColumn("runtimeName", "runtimeName"),
        DataExportColumn("runtimeNodeName", "runtimeNodeName"),
        DataExportColumn("infrastructureNodeId", "infrastructureNodeId"),
        DataExportColumn("runtimeNodeRole", "runtimeNodeRole"),
        DataExportColumn("nodeReady", "nodeReady"),
        DataExportColumn("runtimeEngineVersion", "runtimeEngineVersion"),
        DataExportColumn("containerEngineVersion", "containerEngineVersion"),
        DataExportColumn("operatingSystem", "operatingSystem"),
        DataExportColumn("architecture", "architecture"),
        DataExportColumn("inventoryHash", "inventoryHash"),
        DataExportColumn("discoveredAt", "discoveredAt"),
        DataExportColumn("recordedAt", "recordedAt")
    )
}
