package tech.medo.fileupload.markstagedfileconsumed

import tech.medo.fileupload.markstagedfileconsumed.MarkStagedFileConsumedCommand

import tech.medo.fileupload.events.StagedFileConsumedEvent
import tech.medo.fileupload.stagedfile.StagedFileState


import tech.medo.fileupload.domain.states.StagedFileStateEnum


interface MarkStagedFileConsumedDecision {
    fun decide(command: MarkStagedFileConsumedCommand, state: StagedFileState): List<Any> {
        require(state.currentState == StagedFileStateEnum.STAGED) {
            "MarkStagedFileConsumed requires StagedFile to be Staged."
        }
        return listOf(
            StagedFileConsumedEvent(stagedFileId = command.stagedFileId, consumedByContext = command.consumedByContext, consumedByCommand = command.consumedByCommand, consumedByCommandId = command.consumedByCommandId)
        )
    }
}
