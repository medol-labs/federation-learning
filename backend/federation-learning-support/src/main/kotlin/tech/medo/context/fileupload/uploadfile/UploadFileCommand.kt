package tech.medo.fileupload.uploadfile

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.uploadedfile.UploadedFileSelection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class UploadFileCommand(
    val fileId: UUID = java.util.UUID.randomUUID(),
    val uploadedFile: String,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val fileLocation: String,
    val checksum: String?,
    val expiresAt: LocalDateTime,
    val purpose: String
) {
    @TargetEntityId
    val selection: UploadedFileSelection = UploadedFileSelection(fileId = fileId)

}
