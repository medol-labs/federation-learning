package tech.medo.dataexchange.dataimportjob

import tech.medo.dataexchange.events.DataImportRequestedEvent
import tech.medo.dataexchange.dataimportjob.MarkDataImportProcessingCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-data-exchange-data-import-job")
@Component
class ExecuteDataImportProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: DataImportRequestedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(MarkDataImportProcessingCommand(dataImportJobId = event.dataImportJobId, totalRows = 0L /* TODO: provide totalRows */, status = event.status)).resultMessage
}
