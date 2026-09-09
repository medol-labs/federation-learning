package tech.medo.domain.fileupload.downloadfile

import org.springframework.stereotype.Component
import tech.medo.fileupload.domain.states.UploadedFileStateEnum
import tech.medo.fileupload.downloadfile.DownloadFileCommand
import tech.medo.fileupload.downloadfile.DownloadFileDecision
import tech.medo.fileupload.downloadfile.DownloadFileResult
import tech.medo.fileupload.events.FileDownloadAuthorizedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState

@Component
class DownloadFileDecisionComponent : DownloadFileDecision {
    override fun decide(
        command: DownloadFileCommand,
        state: UploadedFileState,
        portResult: DownloadFileResult
    ): List<Any> {
        require(
            state.currentState == UploadedFileStateEnum.AVAILABLE ||
                state.currentState == UploadedFileStateEnum.REFERENCED
        ) {
            "File is not available for download."
        }
        return when (portResult) {
            is DownloadFileResult.Succeeded -> listOf(
                FileDownloadAuthorizedEvent(
                    fileId = command.fileId,
                    originalFileName = portResult.originalFileName,
                    contentType = portResult.contentType,
                    sizeBytes = portResult.sizeBytes,
                    downloadUri = portResult.downloadUri
                )
            )
        }
    }
}
