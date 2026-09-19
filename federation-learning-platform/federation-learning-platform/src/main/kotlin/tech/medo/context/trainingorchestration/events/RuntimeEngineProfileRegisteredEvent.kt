package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeEngineProfileRegisteredEvent(
    val runtimeEngineProfileId: UUID,
    @EventTag(key = "profileName")
    val profileName: String,
    val pluginProfile: String,
    val runtimeEngineImage: String,
    val imageDigest: String?,
    val supportedModelPluginsDescription: String?,
    val supportedAggregationAlgorithmsDescription: String?,
    val active: Boolean
)
