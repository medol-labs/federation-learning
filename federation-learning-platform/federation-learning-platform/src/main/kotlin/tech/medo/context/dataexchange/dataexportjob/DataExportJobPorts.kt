package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Component
import tech.medo.shared.application.export.DataExportJobLifecyclePort
import tech.medo.shared.application.export.DataExportJobRequestMessage
import tech.medo.shared.application.export.DataExportJobRequestPort
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@Component
class AxonDataExportJobRequestPort(
    private val commandGateway: CommandGateway
) : DataExportJobRequestPort {
    override fun request(message: DataExportJobRequestMessage) {
        commandGateway.send(
            RequestDataExportCommand(
                dataExportJobId = message.dataExportJobId,
                resourceName = message.resourceName,
                criteriaJson = message.criteriaJson,
                sortJson = message.sortJson,
                columnsJson = message.columnsJson,
                requestedLocale = message.requestedLocale,
                requestedAt = LocalDateTime.ofInstant(message.requestedAt, ZoneOffset.UTC),
                snapshotUpperBound = LocalDateTime.ofInstant(message.snapshotUpperBound, ZoneOffset.UTC),
                requestHash = message.requestHash,
                fileName = message.fileName,
                status = DataExportJobStatus.REQUESTED
            )
        ).resultMessage.toCompletableFuture().join()
    }
}

@Component
class AxonDataExportJobLifecyclePort(
    private val commandGateway: CommandGateway
) : DataExportJobLifecyclePort {
    override fun markProcessing(dataExportJobId: UUID) {
        commandGateway.send(MarkDataExportProcessingCommand(dataExportJobId, DataExportJobStatus.PROCESSING)).resultMessage.toCompletableFuture().join()
    }

    override fun complete(dataExportJobId: UUID, fileName: String, filePath: String, rowCount: Long) {
        commandGateway.send(CompleteDataExportCommand(dataExportJobId, fileName, filePath, rowCount, DataExportJobStatus.COMPLETED)).resultMessage.toCompletableFuture().join()
    }

    override fun fail(dataExportJobId: UUID, errorMessage: String) {
        commandGateway.send(FailDataExportCommand(dataExportJobId, errorMessage, DataExportJobStatus.FAILED)).resultMessage.toCompletableFuture().join()
    }
}

object DataExportJobStatus {
    const val REQUESTED = "REQUESTED"
    const val PROCESSING = "PROCESSING"
    const val COMPLETED = "COMPLETED"
    const val FAILED = "FAILED"
}
