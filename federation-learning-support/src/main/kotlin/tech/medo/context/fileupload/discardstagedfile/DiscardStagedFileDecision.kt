package tech.medo.fileupload.discardstagedfile

import tech.medo.fileupload.discardstagedfile.DiscardStagedFileCommand

import tech.medo.fileupload.events.StagedFileDiscardedEvent
import tech.medo.fileupload.stagedfile.StagedFileState


import tech.medo.fileupload.domain.states.StagedFileStateEnum


interface DiscardStagedFileDecision {
    fun decide(command: DiscardStagedFileCommand, state: StagedFileState): List<Any> {
        require(state.currentState == StagedFileStateEnum.STAGED) {
            "DiscardStagedFile requires StagedFile to be Staged."
        }
        return listOf(
            StagedFileDiscardedEvent(stagedFileId = command.stagedFileId, discardReason = command.discardReason)
        )
    }
}
