package tech.medo.fileupload.markfilereferenced

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.fileupload.markfilereferenced.MarkFileReferencedCommand

import tech.medo.fileupload.uploadedfile.UploadedFileState



@Component
class MarkFileReferencedCommandHandler(
    private val decision: MarkFileReferencedDecision
) {
    @CommandHandler
    fun handle(
        command: MarkFileReferencedCommand,
        @InjectEntity(idProperty = "fileId") state: UploadedFileState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
