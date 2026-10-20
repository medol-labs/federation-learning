package tech.medo.dataexchange.dataimportjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.dataexchange.dataimportjob.RequestDataImportCommand
import tech.medo.dataexchange.dataimportjob.MarkDataImportProcessingCommand
import tech.medo.dataexchange.dataimportjob.CompleteDataImportCommand
import tech.medo.dataexchange.dataimportjob.FailDataImportCommand







@Component
class DataImportJobCommandHandler(
    private val decision: DataImportJobDecision
) {
    @CommandHandler
    fun handle(
        command: RequestDataImportCommand,
        eventAppender: EventAppender
    ) {

        eventAppender.append(decision.decide(command))

    }

    @CommandHandler
    fun handle(
        command: MarkDataImportProcessingCommand,
        @InjectEntity(idProperty = "dataImportJobId") state: DataImportJobState,
        eventAppender: EventAppender
    ) {

        eventAppender.append(decision.decide(command, state))

    }

    @CommandHandler
    fun handle(
        command: CompleteDataImportCommand,
        @InjectEntity(idProperty = "dataImportJobId") state: DataImportJobState,
        eventAppender: EventAppender
    ) {

        eventAppender.append(decision.decide(command, state))

    }

    @CommandHandler
    fun handle(
        command: FailDataImportCommand,
        @InjectEntity(idProperty = "dataImportJobId") state: DataImportJobState,
        eventAppender: EventAppender
    ) {

        eventAppender.append(decision.decide(command, state))

    }
}
