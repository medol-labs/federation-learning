package tech.medo.fileupload.discardstagedfile

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.fileupload.stagedfile.StagedFileSelection
import java.util.UUID;


@Command
data class DiscardStagedFileCommand(
    val stagedFileId: UUID,
    val discardReason: String?
) {
    @TargetEntityId
    val selection: StagedFileSelection = StagedFileSelection(stagedFileId = stagedFileId)

}
