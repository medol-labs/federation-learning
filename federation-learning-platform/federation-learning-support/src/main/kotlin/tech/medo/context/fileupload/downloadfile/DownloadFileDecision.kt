package tech.medo.fileupload.downloadfile

import tech.medo.fileupload.downloadfile.DownloadFileCommand

import tech.medo.fileupload.downloadfile.DownloadFileResult
import tech.medo.fileupload.events.FileDownloadAuthorizedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState





interface DownloadFileDecision {
    fun decide(command: DownloadFileCommand, state: UploadedFileState, portResult: DownloadFileResult): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is DownloadFileResult.Succeeded -> listOf(FileDownloadAuthorizedEvent(fileId = command.fileId, originalFileName = portResult.originalFileName, contentType = portResult.contentType, sizeBytes = portResult.sizeBytes, downloadUri = portResult.downloadUri))
                }
    }
}
