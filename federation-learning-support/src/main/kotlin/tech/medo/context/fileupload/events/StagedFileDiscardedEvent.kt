package tech.medo.fileupload.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class StagedFileDiscardedEvent(
    @EventTag(key = "stagedFileId")
    val stagedFileId: UUID,
    val discardReason: String?
)
