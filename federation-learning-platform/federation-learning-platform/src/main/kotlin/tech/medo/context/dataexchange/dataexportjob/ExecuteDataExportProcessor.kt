package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.dataexchange.events.DataExportRequestedEvent
import tech.medo.shared.application.export.DataExportExecutionTask
import tech.medo.shared.application.export.DataExportJobLifecyclePort
import tech.medo.shared.application.export.DataExportResourceExecutorRegistry

@Namespace("automation-data-exchange-execute-data-export")
@Component
class ExecuteDataExportProcessor(
    private val registry: DataExportResourceExecutorRegistry,
    private val lifecyclePort: DataExportJobLifecyclePort
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @EventHandler
    fun on(event: DataExportRequestedEvent) {
        val task = DataExportExecutionTask(
            dataExportJobId = event.dataExportJobId,
            resourceName = event.resourceName,
            criteriaJson = event.criteriaJson,
            sortJson = event.sortJson,
            columnsJson = event.columnsJson,
            requestedLocale = event.requestedLocale,
            requestedAt = event.requestedAt,
            snapshotUpperBound = event.snapshotUpperBound,
            requestHash = event.requestHash,
            fileName = event.fileName
        )
        try {
            lifecyclePort.markProcessing(event.dataExportJobId)
            val result = registry.executor(event.resourceName).execute(task)
            lifecyclePort.complete(event.dataExportJobId, result.fileName, result.filePath, result.rowCount)
        } catch (ex: Exception) {
            log.warn("Data export job {} failed", event.dataExportJobId, ex)
            lifecyclePort.fail(event.dataExportJobId, ex.message ?: "Data export failed.")
        }
    }
}
