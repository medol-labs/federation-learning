package tech.medo.modellifecycle.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ModelPromotedToProductionEvent(
    @EventTag(key = "modelId")
    val modelId: UUID,
    val releaseChannel: String,
    val productionStage: String
)
