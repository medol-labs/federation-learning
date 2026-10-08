package tech.medo.dataexchange.dataimportjob

import tech.medo.dataexchange.dataimportjob.RequestDataImportCommand
import tech.medo.dataexchange.dataimportjob.MarkDataImportProcessingCommand
import tech.medo.dataexchange.dataimportjob.CompleteDataImportCommand
import tech.medo.dataexchange.dataimportjob.FailDataImportCommand


import tech.medo.dataexchange.events.DataImportRequestedEvent
import tech.medo.dataexchange.events.DataImportProcessingStartedEvent
import tech.medo.dataexchange.events.DataImportCompletedEvent
import tech.medo.dataexchange.events.DataImportFailedEvent





interface DataImportJobDecision {
    fun decide(command: RequestDataImportCommand): List<Any> {
        return listOf(
            DataImportRequestedEvent(dataImportJobId = command.dataImportJobId, commandName = command.commandName, resourceName = command.resourceName, originalFileName = command.originalFileName, filePath = command.filePath, requestedLocale = command.requestedLocale, requestedAt = command.requestedAt, requestHash = command.requestHash, status = command.status)
        )
    }

    fun decide(command: MarkDataImportProcessingCommand, state: DataImportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataImportProcessingStartedEvent(dataImportJobId = command.dataImportJobId, totalRows = command.totalRows, status = command.status)
        )
    }

    fun decide(command: CompleteDataImportCommand, state: DataImportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataImportCompletedEvent(dataImportJobId = command.dataImportJobId, totalRows = command.totalRows, successRows = command.successRows, failedRows = command.failedRows, failureIndexPath = command.failureIndexPath, status = command.status)
        )
    }

    fun decide(command: FailDataImportCommand, state: DataImportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataImportFailedEvent(dataImportJobId = command.dataImportJobId, errorMessage = command.errorMessage, status = command.status)
        )
    }
}
