package tech.medo.fileupload.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FileReferencedEvent(
    @EventTag(key = "fileId")
    val fileId: UUID,
    val referencedByContext: String,
    val referencedByCommand: String,
    val referencedByCommandId: UUID?
)
