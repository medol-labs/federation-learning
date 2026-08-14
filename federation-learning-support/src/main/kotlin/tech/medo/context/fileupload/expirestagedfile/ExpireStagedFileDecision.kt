package tech.medo.fileupload.expirestagedfile

import tech.medo.fileupload.expirestagedfile.ExpireStagedFileCommand

import tech.medo.fileupload.events.StagedFileExpiredEvent
import tech.medo.fileupload.stagedfile.StagedFileState


import tech.medo.fileupload.domain.states.StagedFileStateEnum


interface ExpireStagedFileDecision {
    fun decide(command: ExpireStagedFileCommand, state: StagedFileState): List<Any> {
        require(state.currentState == StagedFileStateEnum.STAGED) {
            "ExpireStagedFile requires StagedFile to be Staged."
        }
        return listOf(
            StagedFileExpiredEvent(stagedFileId = command.stagedFileId, expiredAt = command.expiredAt, expirationReason = command.expirationReason)
        )
    }
}
