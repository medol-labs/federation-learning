package tech.medo.fileupload.discardfile

import tech.medo.fileupload.discardfile.DiscardFileCommand


import tech.medo.fileupload.events.FileDiscardedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState


import tech.medo.fileupload.domain.states.UploadedFileStateEnum


interface DiscardFileDecision {
    fun decide(command: DiscardFileCommand, state: UploadedFileState): List<Any> {
        require(state.currentState == UploadedFileStateEnum.AVAILABLE) {
            "DiscardFile requires UploadedFile to be Available."
        }
        return listOf(
            FileDiscardedEvent(fileId = command.fileId, discardReason = command.discardReason)
        )
    }
}
