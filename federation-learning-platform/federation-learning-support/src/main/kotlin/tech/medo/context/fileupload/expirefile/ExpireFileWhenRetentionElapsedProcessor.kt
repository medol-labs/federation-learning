package tech.medo.fileupload.expirefile

import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.expirefile.ExpireFileCommand
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-file-upload-expire-file")
@Component
class ExpireFileWhenRetentionElapsedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: FileUploadedEvent): java.util.concurrent.CompletableFuture<*> =
        if (false) {
            commandGateway.send(ExpireFileCommand(fileId = event.fileId, expiredAt = java.time.LocalDateTime.now() /* TODO: provide expiredAt */, expirationReason = "" /* TODO: provide expirationReason */)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
