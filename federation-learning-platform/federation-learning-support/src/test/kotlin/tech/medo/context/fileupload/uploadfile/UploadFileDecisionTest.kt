package tech.medo.fileupload.uploadfile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.uploadfile.UploadFileCommand
import tech.medo.fileupload.events.FileUploadedEvent
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class UploadFileDecisionTest {
    @Test
    fun UploadFileForModelImport() {


        val command = UploadFileCommand(
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray()),
            uploadedFile = "file://credit-risk-initial.onnx",
            originalFileName = "credit-risk-initial.onnx",
            contentType = "application/octet-stream",
            sizeBytes = 2048L,
            fileLocation = "file://uploads/file-1/credit-risk-initial.onnx",
            checksum = "sha256:model",
            expiresAt = java.time.LocalDateTime.parse("2026-07-25T12:00:00"),
            purpose = "MODEL_ARTIFACT_IMPORT"
        )

        val events = (object : UploadFileDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<FileUploadedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("file-1".toByteArray()), event.fileId)
        assertEquals("credit-risk-initial.onnx", event.originalFileName)
        assertEquals("application/octet-stream", event.contentType)
        assertEquals(2048L, event.sizeBytes)
        assertEquals("MODEL_ARTIFACT_IMPORT", event.purpose)
        assertEquals("file://uploads/file-1/credit-risk-initial.onnx", event.fileLocation)
        assertEquals("sha256:model", event.checksum)
        assertEquals(java.time.LocalDateTime.parse("2026-07-25T12:00:00"), event.expiresAt)
    }
}
