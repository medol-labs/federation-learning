package tech.medo.fileupload.discardfile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.discardfile.DiscardFileCommand
import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.events.FileDiscardedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class DiscardFileDecisionTest {
    @Test
    fun DiscardUnreferencedFile() {
        val state = UploadedFileState()
        state.evolve(
            FileUploadedEvent(
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray()),
            originalFileName = "",
            contentType = null,
            sizeBytes = null,
            purpose = "",
            fileLocation = "",
            checksum = null,
            expiresAt = java.time.LocalDateTime.now()
            )
        )

        val command = DiscardFileCommand(
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray()),
            discardReason = "Wrong file selected."
        )

        val events = (object : DiscardFileDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<FileDiscardedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("file-1".toByteArray()), event.fileId)
        assertEquals("Wrong file selected.", event.discardReason)
    }
}
