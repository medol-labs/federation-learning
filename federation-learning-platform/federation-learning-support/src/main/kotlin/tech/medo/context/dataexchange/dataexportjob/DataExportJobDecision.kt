package tech.medo.dataexchange.dataexportjob

import tech.medo.dataexchange.dataexportjob.RequestDataExportCommand
import tech.medo.dataexchange.dataexportjob.MarkDataExportProcessingCommand
import tech.medo.dataexchange.dataexportjob.CompleteDataExportCommand
import tech.medo.dataexchange.dataexportjob.FailDataExportCommand


import tech.medo.dataexchange.events.DataExportRequestedEvent
import tech.medo.dataexchange.events.DataExportProcessingStartedEvent
import tech.medo.dataexchange.events.DataExportCompletedEvent
import tech.medo.dataexchange.events.DataExportFailedEvent





interface DataExportJobDecision {
    fun decide(command: RequestDataExportCommand): List<Any> {
        return listOf(
            DataExportRequestedEvent(dataExportJobId = command.dataExportJobId, resourceName = command.resourceName, criteriaJson = command.criteriaJson, sortJson = command.sortJson, columnsJson = command.columnsJson, requestedLocale = command.requestedLocale, requestedAt = command.requestedAt, snapshotUpperBound = command.snapshotUpperBound, requestHash = command.requestHash, fileName = command.fileName, status = command.status)
        )
    }

    fun decide(command: MarkDataExportProcessingCommand, state: DataExportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataExportProcessingStartedEvent(dataExportJobId = command.dataExportJobId, status = command.status)
        )
    }

    fun decide(command: CompleteDataExportCommand, state: DataExportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataExportCompletedEvent(dataExportJobId = command.dataExportJobId, fileName = command.fileName, filePath = command.filePath, rowCount = command.rowCount, status = command.status)
        )
    }

    fun decide(command: FailDataExportCommand, state: DataExportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataExportFailedEvent(dataExportJobId = command.dataExportJobId, errorMessage = command.errorMessage, status = command.status)
        )
    }
}
