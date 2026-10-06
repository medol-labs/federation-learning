package tech.medo.runtimemonitoring.runtimetelemetrylatest

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
@RequestMapping("/noderuntimehealth/runtimetelemetrylatest")
class RuntimeTelemetryLatestReadModelResource(
    private val repository: RuntimeTelemetryLatestReadModelRepository,
    private val dataExportService: DataExportService
) {
    private val exportColumns = listOf(
        DataExportColumn("nodeId", "nodeId"),
        DataExportColumn("runtimeAgentId", "runtimeAgentId"),
        DataExportColumn("federationId", "federationId"),
        DataExportColumn("federationName", "federationName"),
        DataExportColumn("trainingJobId", "trainingJobId"),
        DataExportColumn("trainingJobObjective", "trainingJobObjective"),
        DataExportColumn("roundExecutionId", "roundExecutionId"),
        DataExportColumn("runtimeNodeName", "runtimeNodeName"),
        DataExportColumn("cpuLoad", "cpuLoad"),
        DataExportColumn("gpuLoad", "gpuLoad"),
        DataExportColumn("memoryLoad", "memoryLoad"),
        DataExportColumn("lastHeartbeatAt", "lastHeartbeatAt"),
        DataExportColumn("lastRecoveredAt", "lastRecoveredAt"),
        DataExportColumn("offlineDetectionPending", "offlineDetectionPending"),
        DataExportColumn("recoveryDetectionPending", "recoveryDetectionPending"),
        DataExportColumn("resourcePressureDetectionPending", "resourcePressureDetectionPending"),
        DataExportColumn("offlineReason", "offlineReason"),
        DataExportColumn("recoveryReason", "recoveryReason"),
        DataExportColumn("pressureType", "pressureType"),
        DataExportColumn("observedValue", "observedValue"),
        DataExportColumn("thresholdValue", "thresholdValue"),
        DataExportColumn("alertSeverity", "alertSeverity"),
        DataExportColumn("alertMessage", "alertMessage"),
        DataExportColumn("healthStatus", "healthStatus"),
        DataExportColumn("telemetryRetentionPolicy", "telemetryRetentionPolicy")
    )

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_telemetry_latest:list') or hasAuthority('runtime_telemetry_latest:read')")
    @GetMapping
    fun findAll(
        criteria: RuntimeTelemetryLatestReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): Page<RuntimeTelemetryLatestReadModel> =
        findPage(criteria, pageable)

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_telemetry_latest:export') or hasAuthority('runtime_telemetry_latest:list') or hasAuthority('runtime_telemetry_latest:read')")
    @PostMapping("/export")
    fun export(
        @RequestBody(required = false) request: DataExportRequest?,
        criteria: RuntimeTelemetryLatestReadModelCriteria,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Any> {
        val columns = dataExportService.validatedColumns(request?.columns, exportColumns)
        val snapshotUpperBound = LocalDateTime.now(ZoneOffset.UTC)
        val snapshotCriteria = applyExportSnapshot(criteria, snapshotUpperBound)
        val exportPageable = PageRequest.of(0, dataExportService.pageSize(), pageable.sort)
        val firstPage = findPage(snapshotCriteria, exportPageable)
        return dataExportService.export("runtimetelemetrylatest", columns, snapshotCriteria, pageable.sort, firstPage, fetchPage = { nextPage ->
            findPage(snapshotCriteria, nextPage)
        }, snapshotUpperBound = snapshotUpperBound.toInstant(ZoneOffset.UTC))
    }


    private fun findPage(criteria: RuntimeTelemetryLatestReadModelCriteria, pageable: Pageable): Page<RuntimeTelemetryLatestReadModel> =
        repository.findAllByCriteria(criteria, pageable)

    private fun applyExportSnapshot(criteria: RuntimeTelemetryLatestReadModelCriteria, snapshotUpperBound: LocalDateTime): RuntimeTelemetryLatestReadModelCriteria {
        val projectionUpdatedAt = criteria.projectionUpdatedAt ?: RangeFilter<LocalDateTime>().also {
            criteria.projectionUpdatedAt = it
        }
        val requestedUpperBound = projectionUpdatedAt.getLessThanOrEqual()
        if (requestedUpperBound == null || requestedUpperBound.isAfter(snapshotUpperBound)) {
            projectionUpdatedAt.setLessThanOrEqual(snapshotUpperBound)
        }
        return criteria
    }


    @PreAuthorize("hasAuthority('*:*') or hasAuthority('runtime_telemetry_latest:read')")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<RuntimeTelemetryLatestReadModel> =
        repository.findById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}

@Component
class RuntimeTelemetryLatestDataExportExecutor(
    private val repository: RuntimeTelemetryLatestReadModelRepository,
    private val dataExportService: DataExportService,
    private val objectMapper: ObjectMapper
) : DataExportResourceExecutor {
    override val resourceName: String = "runtimetelemetrylatest"

    override fun execute(task: DataExportExecutionTask) =
        dataExportService.writeCsvFile(
            fileName = task.fileName,
            columns = dataExportService.columnsFromJson(task.columnsJson, exportColumns),
            firstPage = firstPage(task),
            fetchPage = { nextPage -> repository.findAllByCriteria(criteria(task), nextPage) }
        )

    private fun firstPage(task: DataExportExecutionTask): Page<RuntimeTelemetryLatestReadModel> {
        val pageable = PageRequest.of(0, dataExportService.pageSize(), dataExportService.sortFromJson(task.sortJson))
        return repository.findAllByCriteria(criteria(task), pageable)
    }

    private fun criteria(task: DataExportExecutionTask): RuntimeTelemetryLatestReadModelCriteria {
        val criteria = objectMapper.readValue(task.criteriaJson, RuntimeTelemetryLatestReadModelCriteria::class.java)
        val snapshotUpperBound = LocalDateTime.ofInstant(task.snapshotUpperBound, ZoneOffset.UTC)
        applyExportSnapshot(criteria, snapshotUpperBound)
        return criteria
    }

    private fun applyExportSnapshot(criteria: RuntimeTelemetryLatestReadModelCriteria, snapshotUpperBound: LocalDateTime): RuntimeTelemetryLatestReadModelCriteria {
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
        DataExportColumn("runtimeAgentId", "runtimeAgentId"),
        DataExportColumn("federationId", "federationId"),
        DataExportColumn("federationName", "federationName"),
        DataExportColumn("trainingJobId", "trainingJobId"),
        DataExportColumn("trainingJobObjective", "trainingJobObjective"),
        DataExportColumn("roundExecutionId", "roundExecutionId"),
        DataExportColumn("runtimeNodeName", "runtimeNodeName"),
        DataExportColumn("cpuLoad", "cpuLoad"),
        DataExportColumn("gpuLoad", "gpuLoad"),
        DataExportColumn("memoryLoad", "memoryLoad"),
        DataExportColumn("lastHeartbeatAt", "lastHeartbeatAt"),
        DataExportColumn("lastRecoveredAt", "lastRecoveredAt"),
        DataExportColumn("offlineDetectionPending", "offlineDetectionPending"),
        DataExportColumn("recoveryDetectionPending", "recoveryDetectionPending"),
        DataExportColumn("resourcePressureDetectionPending", "resourcePressureDetectionPending"),
        DataExportColumn("offlineReason", "offlineReason"),
        DataExportColumn("recoveryReason", "recoveryReason"),
        DataExportColumn("pressureType", "pressureType"),
        DataExportColumn("observedValue", "observedValue"),
        DataExportColumn("thresholdValue", "thresholdValue"),
        DataExportColumn("alertSeverity", "alertSeverity"),
        DataExportColumn("alertMessage", "alertMessage"),
        DataExportColumn("healthStatus", "healthStatus"),
        DataExportColumn("telemetryRetentionPolicy", "telemetryRetentionPolicy")
    )
}
