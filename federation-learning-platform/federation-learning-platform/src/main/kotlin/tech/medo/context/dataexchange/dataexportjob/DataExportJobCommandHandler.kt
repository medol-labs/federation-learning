package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component

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
        @InjectEntity(idProperty = "selection") state: DataExportJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }

    @CommandHandler
    fun handle(
        command: CompleteDataExportCommand,
        @InjectEntity(idProperty = "selection") state: DataExportJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }

    @CommandHandler
    fun handle(
        command: FailDataExportCommand,
        @InjectEntity(idProperty = "selection") state: DataExportJobState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
