package tech.medo.fileupload.discardfile

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.fileupload.discardfile.DiscardFileCommand

import tech.medo.fileupload.uploadedfile.UploadedFileState



@Component
class DiscardFileCommandHandler(
    private val decision: DiscardFileDecision
) {
    @CommandHandler
    fun handle(
        command: DiscardFileCommand,
        @InjectEntity(idProperty = "fileId") state: UploadedFileState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
