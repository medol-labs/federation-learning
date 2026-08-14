package tech.medo.fileupload.stagefileupload

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.stagefileupload.StageFileUploadCommand
import tech.medo.fileupload.events.FileUploadStagedEvent
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class StageFileUploadDecisionTest {
    @Test
    fun StageUploadForModelImport() {


        val command = StageFileUploadCommand(
            stagedFileId = UUID.nameUUIDFromBytes("staged-file-1".toByteArray()),
            uploadedFile = "file://credit-risk-initial.onnx",
            originalFileName = "credit-risk-initial.onnx",
            contentType = "application/octet-stream",
            sizeBytes = 2048L,
            stagedFileLocation = "file://staged/staged-file-1/credit-risk-initial.onnx",
            checksum = "sha256:model",
            expiresAt = java.time.LocalDateTime.parse("2026-07-25T12:00:00"),
            purpose = "MODEL_ARTIFACT_IMPORT"
        )

        val events = (object : StageFileUploadDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<FileUploadStagedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("staged-file-1".toByteArray()), event.stagedFileId)
        assertEquals("credit-risk-initial.onnx", event.originalFileName)
        assertEquals("application/octet-stream", event.contentType)
        assertEquals(2048L, event.sizeBytes)
        assertEquals("MODEL_ARTIFACT_IMPORT", event.purpose)
        assertEquals("file://staged/staged-file-1/credit-risk-initial.onnx", event.stagedFileLocation)
        assertEquals("sha256:model", event.checksum)
        assertEquals(java.time.LocalDateTime.parse("2026-07-25T12:00:00"), event.expiresAt)
    }
}
