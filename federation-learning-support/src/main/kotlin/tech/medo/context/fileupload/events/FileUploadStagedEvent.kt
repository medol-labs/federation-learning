package tech.medo.fileupload.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



@Event
data class FileUploadStagedEvent(
    @EventTag(key = "stagedFileId")
    val stagedFileId: UUID,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val purpose: String,
    val stagedFileLocation: String,
    val checksum: String?,
    val expiresAt: LocalDateTime
)
