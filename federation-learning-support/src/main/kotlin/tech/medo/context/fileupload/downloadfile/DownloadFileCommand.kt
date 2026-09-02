package tech.medo.fileupload.downloadfile

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.uploadedfile.UploadedFileSelection
import java.util.UUID;


@Command
data class DownloadFileCommand(
    val fileId: UUID
) {
    @TargetEntityId
    val selection: UploadedFileSelection = UploadedFileSelection(fileId = fileId)

}
