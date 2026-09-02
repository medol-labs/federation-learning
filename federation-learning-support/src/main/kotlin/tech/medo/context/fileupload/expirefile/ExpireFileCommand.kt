package tech.medo.fileupload.expirefile

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.uploadedfile.UploadedFileSelection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class ExpireFileCommand(
    val fileId: UUID,
    val expiredAt: LocalDateTime,
    val expirationReason: String
) {
    @TargetEntityId
    val selection: UploadedFileSelection = UploadedFileSelection(fileId = fileId)

}
