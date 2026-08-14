package tech.medo.fileupload.expirestagedfile

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.stagedfile.StagedFileSelection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class ExpireStagedFileCommand(
    val stagedFileId: UUID,
    val expiredAt: LocalDateTime,
    val expirationReason: String
) {
    @TargetEntityId
    val selection: StagedFileSelection = StagedFileSelection(stagedFileId = stagedFileId)

}
