package tech.medo.fileupload.downloadfile

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.fileupload.downloadfile.DownloadFileCommand
import tech.medo.fileupload.downloadfile.DownloadFileInput
import tech.medo.fileupload.downloadfile.DownloadFileService
import tech.medo.fileupload.uploadedfile.UploadedFileState



@Component
class DownloadFileCommandHandler(
    private val decision: DownloadFileDecision,
    private val downloadFileService: DownloadFileService
) {
    @CommandHandler
    fun handle(
        command: DownloadFileCommand,
        @InjectEntity(idProperty = "fileId") state: UploadedFileState,
        eventAppender: EventAppender
    ) {
        val input = DownloadFileInput(fileId = command.fileId)
        val portResult = downloadFileService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
