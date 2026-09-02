package tech.medo.fileupload.uploadfile

import tech.medo.fileupload.uploadfile.UploadFileCommand


import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState





interface UploadFileDecision {
    fun decide(command: UploadFileCommand): List<Any> {
        return listOf(
            FileUploadedEvent(fileId = command.fileId, originalFileName = command.originalFileName, contentType = command.contentType, sizeBytes = command.sizeBytes, purpose = command.purpose, fileLocation = command.fileLocation, checksum = command.checksum, expiresAt = command.expiresAt)
        )
    }
}
