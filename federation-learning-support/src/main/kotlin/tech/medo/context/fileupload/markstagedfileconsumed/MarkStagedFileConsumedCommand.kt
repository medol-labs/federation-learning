package tech.medo.fileupload.markstagedfileconsumed

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.stagedfile.StagedFileSelection
import java.util.UUID;


@Command
data class MarkStagedFileConsumedCommand(
    val stagedFileId: UUID,
    val consumedByContext: String,
    val consumedByCommand: String,
    val consumedByCommandId: UUID?
) {
    @TargetEntityId
    val selection: StagedFileSelection = StagedFileSelection(stagedFileId = stagedFileId)

}
