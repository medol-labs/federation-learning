package tech.medo.modellifecycle.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ModelApprovedEvent(
    @EventTag(key = "modelVersionId")
    val modelVersionId: UUID,
    val approvalNote: String?
)
