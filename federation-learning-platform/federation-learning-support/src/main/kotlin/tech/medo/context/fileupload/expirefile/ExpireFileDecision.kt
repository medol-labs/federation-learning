package tech.medo.fileupload.expirefile

import tech.medo.fileupload.expirefile.ExpireFileCommand


import tech.medo.fileupload.events.FileExpiredEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState


import tech.medo.fileupload.domain.states.UploadedFileStateEnum


interface ExpireFileDecision {
    fun decide(command: ExpireFileCommand, state: UploadedFileState): List<Any> {
        if (state.currentState != UploadedFileStateEnum.Available) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.fileupload.expireFile.requiresState",
                args = mapOf(
                    "command" to "ExpireFile",
                    "aggregate" to "UploadedFile",
                    "expectedState" to "Available",
                    "actualState" to state.currentState.toString()
                ),
                message = "ExpireFile requires UploadedFile to be Available."
            )
        }
        return listOf(
            FileExpiredEvent(fileId = command.fileId, expiredAt = command.expiredAt, expirationReason = command.expirationReason)
        )
    }
}
