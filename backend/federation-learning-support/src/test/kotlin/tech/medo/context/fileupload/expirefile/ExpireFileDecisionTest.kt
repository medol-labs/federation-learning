package tech.medo.fileupload.expirefile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.expirefile.ExpireFileCommand
import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.events.FileExpiredEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class ExpireFileDecisionTest {
    @Test
    fun ExpireUploadedFile() {
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

        val command = ExpireFileCommand(
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray()),
            expiredAt = java.time.LocalDateTime.now(),
            expirationReason = "Retention window elapsed."
        )

        val events = (object : ExpireFileDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<FileExpiredEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("file-1".toByteArray()), event.fileId)
        assertEquals(command.expiredAt, event.expiredAt)
        assertEquals("Retention window elapsed.", event.expirationReason)
    }
}
