package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dataexchange.dataexportjob.RequestDataExportCommand
import tech.medo.dataexchange.dataexportjob.MarkDataExportProcessingCommand
import tech.medo.dataexchange.dataexportjob.CompleteDataExportCommand
import tech.medo.dataexchange.dataexportjob.FailDataExportCommand







@Component
class DataExportJobCommandHandler(
    private val decision: DataExportJobDecision
) {
    @CommandHandler
    fun handle(
        command: RequestDataExportCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }

    @CommandHandler
    fun handle(
        command: MarkDataExportProcessingCommand,
        @InjectEntity(idProperty = "dataExportJobId") state: DataExportJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }

    @CommandHandler
    fun handle(
        command: CompleteDataExportCommand,
        @InjectEntity(idProperty = "dataExportJobId") state: DataExportJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }

    @CommandHandler
    fun handle(
        command: FailDataExportCommand,
        @InjectEntity(idProperty = "dataExportJobId") state: DataExportJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
