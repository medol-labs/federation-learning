package tech.medo.fileupload.downloadfile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.downloadfile.DownloadFileCommand
import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.events.FileDownloadAuthorizedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState
import tech.medo.fileupload.downloadfile.DownloadFileResult
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class DownloadFileDecisionTest {
    @Test
    fun AuthorizeAvailableFileDownload() {
        val state = UploadedFileState()
        state.evolve(
            FileUploadedEvent(
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray()),
            originalFileName = "credit-risk-initial.onnx",
            contentType = "application/octet-stream",
            sizeBytes = 2048L,
            purpose = "",
            fileLocation = "file://uploads/file-1/credit-risk-initial.onnx",
            checksum = null,
            expiresAt = java.time.LocalDateTime.now()
            )
        )

        val command = DownloadFileCommand(
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray())
        )

        val events = (object : DownloadFileDecision {}).decide(
            command,
            state = state,
            portResult = DownloadFileResult.Succeeded(
                originalFileName = "",
                contentType = null,
                sizeBytes = null,
                downloadUri = ""
            )
        )

        val event = events.filterIsInstance<FileDownloadAuthorizedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("file-1".toByteArray()), event.fileId)
    }
}
