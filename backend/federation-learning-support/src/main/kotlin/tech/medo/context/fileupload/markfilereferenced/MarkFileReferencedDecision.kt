package tech.medo.fileupload.markfilereferenced

import tech.medo.fileupload.markfilereferenced.MarkFileReferencedCommand


import tech.medo.fileupload.events.FileReferencedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState


import tech.medo.fileupload.domain.states.UploadedFileStateEnum


interface MarkFileReferencedDecision {
    fun decide(command: MarkFileReferencedCommand, state: UploadedFileState): List<Any> {
        require(state.currentState == UploadedFileStateEnum.AVAILABLE) {
            "MarkFileReferenced requires UploadedFile to be Available."
        }
        return listOf(
            FileReferencedEvent(fileId = command.fileId, referencedByContext = command.referencedByContext, referencedByCommand = command.referencedByCommand, referencedByCommandId = command.referencedByCommandId)
        )
    }
}
