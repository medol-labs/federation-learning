package tech.medo.dataexchange.downloaddataexportfile

import tech.medo.dataexchange.downloaddataexportfile.DownloadDataExportFileCommand


import tech.medo.dataexchange.events.DataExportFileDownloadAuthorizedEvent
import tech.medo.dataexchange.dataexportjob.DataExportJobState





interface DownloadDataExportFileDecision {
    fun decide(command: DownloadDataExportFileCommand, state: DataExportJobState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DataExportFileDownloadAuthorizedEvent(dataExportJobId = command.dataExportJobId, fileName = command.fileName, downloadUri = command.downloadUri)
        )
    }
}
