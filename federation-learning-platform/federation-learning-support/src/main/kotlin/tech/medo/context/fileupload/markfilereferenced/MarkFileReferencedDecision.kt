package tech.medo.fileupload.markfilereferenced

import tech.medo.fileupload.markfilereferenced.MarkFileReferencedCommand


import tech.medo.fileupload.events.FileReferencedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState


import tech.medo.fileupload.domain.states.UploadedFileStateEnum


interface MarkFileReferencedDecision {
    fun decide(command: MarkFileReferencedCommand, state: UploadedFileState): List<Any> {
        if (state.currentState != UploadedFileStateEnum.Available) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.fileupload.markFileReferenced.requiresState",
                args = mapOf(
                    "command" to "MarkFileReferenced",
                    "aggregate" to "UploadedFile",
                    "expectedState" to "Available",
                    "actualState" to state.currentState.toString()
                ),
                message = "MarkFileReferenced requires UploadedFile to be Available."
            )
        }
        return listOf(
            FileReferencedEvent(fileId = command.fileId, referencedByContext = command.referencedByContext, referencedByCommand = command.referencedByCommand, referencedByCommandId = command.referencedByCommandId)
        )
    }
}
