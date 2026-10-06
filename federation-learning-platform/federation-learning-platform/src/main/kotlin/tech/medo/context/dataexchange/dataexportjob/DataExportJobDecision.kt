package tech.medo.dataexchange.dataexportjob

import tech.medo.dataexchange.events.DataExportCompletedEvent
import tech.medo.dataexchange.events.DataExportFailedEvent
import tech.medo.dataexchange.events.DataExportProcessingStartedEvent
import tech.medo.dataexchange.events.DataExportRequestedEvent

interface DataExportJobDecision {
    fun decide(command: RequestDataExportCommand): List<Any> =
        listOf(
            DataExportRequestedEvent(
                dataExportJobId = command.dataExportJobId,
                resourceName = command.resourceName,
                criteriaJson = command.criteriaJson,
                sortJson = command.sortJson,
                columnsJson = command.columnsJson,
                requestedLocale = command.requestedLocale,
                requestedAt = command.requestedAt,
                snapshotUpperBound = command.snapshotUpperBound,
                requestHash = command.requestHash,
                fileName = command.fileName,
                status = DataExportJobStatus.REQUESTED
            )
        )

    fun decide(command: MarkDataExportProcessingCommand, state: DataExportJobState): List<Any> {
        if (state.status == DataExportJobStatus.PROCESSING || state.status == DataExportJobStatus.COMPLETED) {
            return emptyList()
        }
        return listOf(
            DataExportProcessingStartedEvent(
                dataExportJobId = command.dataExportJobId,
                status = DataExportJobStatus.PROCESSING
            )
        )
    }

    fun decide(command: CompleteDataExportCommand, state: DataExportJobState): List<Any> {
        if (state.status == DataExportJobStatus.COMPLETED) {
            return emptyList()
        }
        return listOf(
            DataExportCompletedEvent(
                dataExportJobId = command.dataExportJobId,
                fileName = command.fileName,
                filePath = command.filePath,
                rowCount = command.rowCount,
                status = DataExportJobStatus.COMPLETED
            )
        )
    }

    fun decide(command: FailDataExportCommand, state: DataExportJobState): List<Any> {
        if (state.status == DataExportJobStatus.COMPLETED || state.status == DataExportJobStatus.FAILED) {
            return emptyList()
        }
        return listOf(
            DataExportFailedEvent(
                dataExportJobId = command.dataExportJobId,
                errorMessage = command.errorMessage,
                status = DataExportJobStatus.FAILED
            )
        )
    }
}

object DataExportJobStatus {
    const val REQUESTED = "REQUESTED"
    const val PROCESSING = "PROCESSING"
    const val COMPLETED = "COMPLETED"
    const val FAILED = "FAILED"
}
