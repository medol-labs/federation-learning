package tech.medo.fileupload.discardfile

import tech.medo.fileupload.discardfile.DiscardFileCommand


import tech.medo.fileupload.events.FileDiscardedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState


import tech.medo.fileupload.domain.states.UploadedFileStateEnum


interface DiscardFileDecision {
    fun decide(command: DiscardFileCommand, state: UploadedFileState): List<Any> {
        if (state.currentState != UploadedFileStateEnum.Available) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.fileupload.discardFile.requiresState",
                args = mapOf(
                    "command" to "DiscardFile",
                    "aggregate" to "UploadedFile",
                    "expectedState" to "Available",
                    "actualState" to state.currentState.toString()
                ),
                message = "DiscardFile requires UploadedFile to be Available."
            )
        }
        return listOf(
            FileDiscardedEvent(fileId = command.fileId, discardReason = command.discardReason)
        )
    }
}
