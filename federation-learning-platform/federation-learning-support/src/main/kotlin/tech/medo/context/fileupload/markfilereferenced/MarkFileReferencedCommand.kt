package tech.medo.fileupload.markfilereferenced

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.uploadedfile.UploadedFileSelection
import java.util.UUID;


@Command
data class MarkFileReferencedCommand(
    val fileId: UUID,
    val referencedByContext: String,
    val referencedByCommand: String,
    val referencedByCommandId: UUID?
) {
    @TargetEntityId
    val selection: UploadedFileSelection = UploadedFileSelection(fileId = fileId)

}
