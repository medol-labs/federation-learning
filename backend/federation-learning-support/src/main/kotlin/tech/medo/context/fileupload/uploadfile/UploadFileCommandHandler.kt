package tech.medo.fileupload.uploadfile

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.fileupload.uploadfile.UploadFileCommand





@Component
class UploadFileCommandHandler(
    private val decision: UploadFileDecision
) {
    @CommandHandler
    fun handle(
        command: UploadFileCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
