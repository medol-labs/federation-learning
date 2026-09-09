package tech.medo.fileupload.expirefile

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.fileupload.expirefile.ExpireFileCommand

import tech.medo.fileupload.uploadedfile.UploadedFileState



@Component
class ExpireFileCommandHandler(
    private val decision: ExpireFileDecision
) {
    @CommandHandler
    fun handle(
        command: ExpireFileCommand,
        @InjectEntity(idProperty = "fileId") state: UploadedFileState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
