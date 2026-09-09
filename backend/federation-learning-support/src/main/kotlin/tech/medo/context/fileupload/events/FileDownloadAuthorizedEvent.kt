package tech.medo.fileupload.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FileDownloadAuthorizedEvent(
    @EventTag(key = "fileId")
    val fileId: UUID,
    val originalFileName: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val downloadUri: String
)
