package tech.medo.runtimemonitoring.runtimehealthdashboard

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
@RequestMapping("/noderuntimehealth/runtimehealthdashboard")
class RuntimeHealthDashboardReadModelResource(
    private val repository: RuntimeHealthDashboardReadModelRepository,
    private val dataExportService: DataExportService
) {
    private val exportColumns = listOf(
        DataExportColumn("nodeId", "nodeId"),
        DataExportColumn("runtimeAgentId", "runtimeAgentId"),
        DataExportColumn("federationId", "federationId"),
        DataExportColumn("trainingJobId", "trainingJobId"),
        DataExportColumn("roundExecutionId", "roundExecutionId"),
        DataExportColumn("federationName", "federationName"),
        DataExportColumn("trainingJobObjective", "trainingJobObjective"),
        DataExportColumn("cpuLoad", "cpuLoad"),
        DataExportColumn("gpuLoad", "gpuLoad"),
        DataExportColumn("memoryLoad", "memoryLoad"),
        DataExportColumn("nodeReady", "nodeReady"),
        DataExportColumn("availableCpuCores", "availableCpuCores"),
        DataExportColumn("availableMemoryGb", "availableMemoryGb"),
        DataExportColumn("availableGpuCount", "availableGpuCount"),
        DataExportColumn("runningWorkloadCount", "runningWorkloadCount"),
        DataExportColumn("workloadCapacity", "workloadCapacity"),
        DataExportColumn("healthStatus", "healthStatus"),
        DataExportColumn("lastHeartbeatAt", "lastHeartbeatAt"),
        DataExportColumn("lastResourceSnapshotAt", "lastResourceSnapshotAt")
    )

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_health_dashboard:list') or hasAuthority('runtime_health_dashboard:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeHealthDashboardReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeHealthDashboardReadModel> =
        findPage(criteria, pageable)

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_health_dashboard:export') or hasAuthority('runtime_health_dashboard:list') or hasAuthority('runtime_health_dashboard:read')")
    @PostMapping("/export")
    fun export(
        @RequestBody(required = false) request: DataExportRequest?,
        criteria: RuntimeHealthDashboardReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Any> {
        val columns = dataExportService.validatedColumns(request?.columns, exportColumns)
        val snapshotUpperBound = LocalDateTime.now(ZoneOffset.UTC)
        val snapshotCriteria = applyExportSnapshot(criteria, snapshotUpperBound)
        val exportPageable = PageRequest.of(0, dataExportService.pageSize(), pageable.sort)
        val firstPage = findPage(snapshotCriteria, exportPageable)
        return dataExportService.export("runtimehealthdashboard", columns, snapshotCriteria, pageable.sort, firstPage, fetchPage = { nextPage ->
            findPage(snapshotCriteria, nextPage)
        }, snapshotUpperBound = snapshotUpperBound.toInstant(ZoneOffset.UTC))
    }


    private fun findPage(criteria: RuntimeHealthDashboardReadModelCriteria, pageable: Pageable): Page<RuntimeHealthDashboardReadModel> =
        repository.findAllByCriteria(criteria, pageable)

    private fun applyExportSnapshot(criteria: RuntimeHealthDashboardReadModelCriteria, snapshotUpperBound: LocalDateTime): RuntimeHealthDashboardReadModelCriteria {
        val projectionUpdatedAt = criteria.projectionUpdatedAt ?: RangeFilter<LocalDateTime>().also {
            criteria.projectionUpdatedAt = it
        }
        val requestedUpperBound = projectionUpdatedAt.getLessThanOrEqual()
        if (requestedUpperBound == null || requestedUpperBound.isAfter(snapshotUpperBound)) {
            projectionUpdatedAt.setLessThanOrEqual(snapshotUpperBound)
        }
        return criteria
    }



    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_health_dashboard:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeHealthDashboardReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}

@Component
class RuntimeHealthDashboardReadModelDataExportExecutor(
    private val repository: RuntimeHealthDashboardReadModelRepository,
    private val dataExportService: DataExportService,
    private val objectMapper: ObjectMapper
) : DataExportResourceExecutor {
    override val resourceName: String = "runtimehealthdashboard"
    private val exportColumns = listOf(
        DataExportColumn("nodeId", "nodeId"),
        DataExportColumn("runtimeAgentId", "runtimeAgentId"),
        DataExportColumn("federationId", "federationId"),
        DataExportColumn("trainingJobId", "trainingJobId"),
        DataExportColumn("roundExecutionId", "roundExecutionId"),
        DataExportColumn("federationName", "federationName"),
        DataExportColumn("trainingJobObjective", "trainingJobObjective"),
        DataExportColumn("cpuLoad", "cpuLoad"),
        DataExportColumn("gpuLoad", "gpuLoad"),
        DataExportColumn("memoryLoad", "memoryLoad"),
        DataExportColumn("nodeReady", "nodeReady"),
        DataExportColumn("availableCpuCores", "availableCpuCores"),
        DataExportColumn("availableMemoryGb", "availableMemoryGb"),
        DataExportColumn("availableGpuCount", "availableGpuCount"),
        DataExportColumn("runningWorkloadCount", "runningWorkloadCount"),
        DataExportColumn("workloadCapacity", "workloadCapacity"),
        DataExportColumn("healthStatus", "healthStatus"),
        DataExportColumn("lastHeartbeatAt", "lastHeartbeatAt"),
        DataExportColumn("lastResourceSnapshotAt", "lastResourceSnapshotAt")
    )

    override fun execute(task: DataExportExecutionTask): DataExportExecutionResult {
        val criteria = objectMapper.readValue<RuntimeHealthDashboardReadModelCriteria>(task.criteriaJson)
        val snapshotUpperBound = LocalDateTime.ofInstant(task.snapshotUpperBound, ZoneOffset.UTC)
        applyExportSnapshot(criteria, snapshotUpperBound)
        val columns = dataExportService.columnsFromJson(task.columnsJson, exportColumns)
        val sort = dataExportService.sortFromJson(task.sortJson)
        val firstPage = repository.findAllByCriteria(criteria, PageRequest.of(0, dataExportService.pageSize(), sort))
        return dataExportService.writeCsvFile(task.fileName, columns, firstPage) { nextPage ->
            repository.findAllByCriteria(criteria, nextPage)
        }
    }

    private fun applyExportSnapshot(criteria: RuntimeHealthDashboardReadModelCriteria, snapshotUpperBound: LocalDateTime): RuntimeHealthDashboardReadModelCriteria {
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
