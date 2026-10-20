package tech.medo.dataexchange.downloaddataexportfile

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dataexchange.downloaddataexportfile.DownloadDataExportFileCommand



import tech.medo.dataexchange.dataexportjob.DataExportJobState



@Component
class DownloadDataExportFileCommandHandler(
    private val decision: DownloadDataExportFileDecision
) {
    @CommandHandler
    fun handle(
        command: DownloadDataExportFileCommand,
        @InjectEntity(idProperty = "dataExportJobId") state: DataExportJobState,
        eventAppender: EventAppender
    ) {

        eventAppender.append(decision.decide(command, state))

    }
}
