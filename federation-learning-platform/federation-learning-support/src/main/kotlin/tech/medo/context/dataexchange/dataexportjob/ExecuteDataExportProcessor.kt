package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.dataexchange.events.DataExportRequestedEvent
import tech.medo.shared.application.export.DataExportExecutionTask
import tech.medo.shared.application.export.DataExportJobLifecyclePort
import tech.medo.shared.application.export.DataExportResourceExecutorRegistry
import tech.medo.shared.application.metadata.MetadataKeys
import java.time.ZoneOffset

@Namespace("automation-federation-learning-support-data-exchange-execute-data-export")
@Component
class ExecuteDataExportProcessor(
    private val registry: DataExportResourceExecutorRegistry,
    private val lifecyclePort: DataExportJobLifecyclePort
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @EventHandler
    fun on(event: DataExportRequestedEvent, message: EventMessage) {
        if (message.metadata()[MetadataKeys.DEPLOYMENT_NAME]?.toString() != AxonDataExportJobRequestPort.DEPLOYMENT_NAME) {
            return
        }

        val task = DataExportExecutionTask(
            dataExportJobId = event.dataExportJobId,
            resourceName = event.resourceName,
            criteriaJson = event.criteriaJson,
            sortJson = event.sortJson,
            columnsJson = event.columnsJson,
            requestedLocale = event.requestedLocale,
            requestedAt = event.requestedAt.toInstant(ZoneOffset.UTC),
            snapshotUpperBound = event.snapshotUpperBound.toInstant(ZoneOffset.UTC),
            requestHash = event.requestHash,
            fileName = event.fileName
        )
        try {
            val result = registry.executor(event.resourceName).execute(task)
            lifecyclePort.complete(event.dataExportJobId, result.fileName, result.filePath, result.rowCount)
        } catch (ex: Exception) {
            log.warn("Data export job {} failed", event.dataExportJobId, ex)
            lifecyclePort.fail(event.dataExportJobId, ex.message ?: "Data export failed.")
        }

    }
}
