package tech.medo.fileupload.discardstagedfile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.discardstagedfile.DiscardStagedFileCommand
import tech.medo.fileupload.events.FileUploadStagedEvent
import tech.medo.fileupload.events.StagedFileDiscardedEvent
import tech.medo.fileupload.stagedfile.StagedFileState
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class DiscardStagedFileDecisionTest {
    @Test
    fun DiscardUnconsumedStagedFile() {
        val state = StagedFileState()
        state.evolve(
            FileUploadStagedEvent(
            stagedFileId = UUID.nameUUIDFromBytes("staged-file-1".toByteArray()),
            originalFileName = "",
            contentType = null,
            sizeBytes = null,
            purpose = "",
            stagedFileLocation = "",
            checksum = null,
            expiresAt = java.time.LocalDateTime.now()
            )
        )

        val command = DiscardStagedFileCommand(
            stagedFileId = UUID.nameUUIDFromBytes("staged-file-1".toByteArray()),
            discardReason = "Wrong file selected."
        )

        val events = (object : DiscardStagedFileDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<StagedFileDiscardedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("staged-file-1".toByteArray()), event.stagedFileId)
        assertEquals("Wrong file selected.", event.discardReason)
    }
}
