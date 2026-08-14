package tech.medo.fileupload.stagefileupload

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.stagedfile.StagedFileSelection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class StageFileUploadCommand(
    val stagedFileId: UUID = java.util.UUID.randomUUID(),
    val uploadedFile: String,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val stagedFileLocation: String,
    val checksum: String?,
    val expiresAt: LocalDateTime,
    val purpose: String
) {
    @TargetEntityId
    val selection: StagedFileSelection = StagedFileSelection(stagedFileId = stagedFileId)

}
