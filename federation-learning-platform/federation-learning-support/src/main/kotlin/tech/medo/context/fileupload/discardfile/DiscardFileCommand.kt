package tech.medo.fileupload.discardfile

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.uploadedfile.UploadedFileSelection
import java.util.UUID;


@Command
data class DiscardFileCommand(
    val fileId: UUID,
    val discardReason: String?
) {
    @TargetEntityId
    val selection: UploadedFileSelection = UploadedFileSelection(fileId = fileId)

}
