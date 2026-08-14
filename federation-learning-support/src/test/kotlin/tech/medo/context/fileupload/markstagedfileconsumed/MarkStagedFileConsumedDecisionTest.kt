package tech.medo.fileupload.markstagedfileconsumed

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.markstagedfileconsumed.MarkStagedFileConsumedCommand
import tech.medo.fileupload.events.FileUploadStagedEvent
import tech.medo.fileupload.events.StagedFileConsumedEvent
import tech.medo.fileupload.stagedfile.StagedFileState
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class MarkStagedFileConsumedDecisionTest {
    @Test
    fun MarkFileConsumedByModelImport() {
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

        val command = MarkStagedFileConsumedCommand(
            stagedFileId = UUID.nameUUIDFromBytes("staged-file-1".toByteArray()),
            consumedByContext = "ModelRepository",
            consumedByCommand = "RegisterModelArtifact",
            consumedByCommandId = null
        )

        val events = (object : MarkStagedFileConsumedDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<StagedFileConsumedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("staged-file-1".toByteArray()), event.stagedFileId)
        assertEquals("ModelRepository", event.consumedByContext)
        assertEquals("RegisterModelArtifact", event.consumedByCommand)
        assertEquals(command.consumedByCommandId, event.consumedByCommandId)
    }
}
