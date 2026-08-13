package tech.medo.modellifecycle.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ModelRolledBackEvent(
    @EventTag(key = "modelId")
    val modelId: UUID,
    val previousModelId: UUID,
    val rollbackReason: String
)
