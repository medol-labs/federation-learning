package tech.medo.fileupload.markfilereferenced

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.fileupload.markfilereferenced.MarkFileReferencedCommand
import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.events.FileReferencedEvent
import tech.medo.fileupload.uploadedfile.UploadedFileState
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class MarkFileReferencedDecisionTest {
    @Test
    fun MarkFileReferencedByModelImport() {
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

        val command = MarkFileReferencedCommand(
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray()),
            referencedByContext = "ModelRepository",
            referencedByCommand = "RegisterModelArtifact",
            referencedByCommandId = null
        )

        val events = (object : MarkFileReferencedDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<FileReferencedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("file-1".toByteArray()), event.fileId)
        assertEquals("ModelRepository", event.referencedByContext)
        assertEquals("RegisterModelArtifact", event.referencedByCommand)
        assertEquals(command.referencedByCommandId, event.referencedByCommandId)
    }
}
