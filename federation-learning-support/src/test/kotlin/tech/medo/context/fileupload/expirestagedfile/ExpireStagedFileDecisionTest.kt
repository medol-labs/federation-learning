package tech.medo.fileupload.expirestagedfile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.expirestagedfile.ExpireStagedFileCommand
import tech.medo.fileupload.events.FileUploadStagedEvent
import tech.medo.fileupload.events.StagedFileExpiredEvent
import tech.medo.fileupload.stagedfile.StagedFileState
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class ExpireStagedFileDecisionTest {
    @Test
    fun ExpireUnconsumedStagedFile() {
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

        val command = ExpireStagedFileCommand(
            stagedFileId = UUID.nameUUIDFromBytes("staged-file-1".toByteArray()),
            expiredAt = java.time.LocalDateTime.now(),
            expirationReason = "Retention window elapsed."
        )

        val events = (object : ExpireStagedFileDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<StagedFileExpiredEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("staged-file-1".toByteArray()), event.stagedFileId)
        assertEquals(command.expiredAt, event.expiredAt)
        assertEquals("Retention window elapsed.", event.expirationReason)
    }
}
