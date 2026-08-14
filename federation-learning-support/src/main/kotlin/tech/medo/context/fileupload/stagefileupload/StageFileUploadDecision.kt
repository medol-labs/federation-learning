package tech.medo.fileupload.stagefileupload

import tech.medo.fileupload.stagefileupload.StageFileUploadCommand

import tech.medo.fileupload.events.FileUploadStagedEvent
import tech.medo.fileupload.stagedfile.StagedFileState





interface StageFileUploadDecision {
    fun decide(command: StageFileUploadCommand): List<Any> {
        return listOf(
            FileUploadStagedEvent(stagedFileId = command.stagedFileId, originalFileName = command.originalFileName, contentType = command.contentType, sizeBytes = command.sizeBytes, purpose = command.purpose, stagedFileLocation = command.stagedFileLocation, checksum = command.checksum, expiresAt = command.expiresAt)
        )
    }
}
