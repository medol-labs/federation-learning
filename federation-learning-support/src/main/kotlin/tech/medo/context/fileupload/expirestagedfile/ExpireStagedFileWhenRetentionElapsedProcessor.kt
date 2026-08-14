package tech.medo.fileupload.expirestagedfile

import tech.medo.fileupload.events.FileUploadStagedEvent
import tech.medo.fileupload.expirestagedfile.ExpireStagedFileCommand
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ExpireStagedFileWhenRetentionElapsedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: FileUploadStagedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ExpireStagedFileCommand(stagedFileId = event.stagedFileId, expiredAt = java.time.LocalDateTime.now() /* TODO: provide expiredAt */, expirationReason = "" /* TODO: provide expirationReason */)).resultMessage
}
