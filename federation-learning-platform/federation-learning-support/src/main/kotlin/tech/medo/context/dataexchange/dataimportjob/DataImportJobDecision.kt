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
            DataImportRequestedEvent(dataImportJobId = command.dataImportJobId, commandName = "" /* TODO: derive value */, resourceName = "" /* TODO: derive value */, originalFileName = "" /* TODO: derive value */, filePath = "" /* TODO: derive value */, requestedLocale = null /* TODO: derive value */, requestedAt = java.time.LocalDateTime.now() /* TODO: derive value */, requestHash = "" /* TODO: derive value */, status = command.status)
        )
    }

    fun decide(command: CompleteDataImportCommand, state: DataImportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataImportRequestedEvent(dataImportJobId = command.dataImportJobId, commandName = "" /* TODO: derive value */, resourceName = "" /* TODO: derive value */, originalFileName = "" /* TODO: derive value */, filePath = "" /* TODO: derive value */, requestedLocale = null /* TODO: derive value */, requestedAt = java.time.LocalDateTime.now() /* TODO: derive value */, requestHash = "" /* TODO: derive value */, status = command.status)
        )
    }

    fun decide(command: FailDataImportCommand, state: DataImportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataImportRequestedEvent(dataImportJobId = command.dataImportJobId, commandName = "" /* TODO: derive value */, resourceName = "" /* TODO: derive value */, originalFileName = "" /* TODO: derive value */, filePath = "" /* TODO: derive value */, requestedLocale = null /* TODO: derive value */, requestedAt = java.time.LocalDateTime.now() /* TODO: derive value */, requestHash = "" /* TODO: derive value */, status = command.status)
        )
    }
}
