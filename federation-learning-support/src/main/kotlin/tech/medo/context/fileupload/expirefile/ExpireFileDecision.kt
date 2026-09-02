package tech.medo.fileupload.expirefile

import tech.medo.fileupload.expirefile.ExpireFileCommand


import tech.medo.fileupload.events.FileExpiredEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState


import tech.medo.fileupload.domain.states.UploadedFileStateEnum


interface ExpireFileDecision {
    fun decide(command: ExpireFileCommand, state: UploadedFileState): List<Any> {
        require(state.currentState == UploadedFileStateEnum.AVAILABLE) {
            "ExpireFile requires UploadedFile to be Available."
        }
        return listOf(
            FileExpiredEvent(fileId = command.fileId, expiredAt = command.expiredAt, expirationReason = command.expirationReason)
        )
    }
}
