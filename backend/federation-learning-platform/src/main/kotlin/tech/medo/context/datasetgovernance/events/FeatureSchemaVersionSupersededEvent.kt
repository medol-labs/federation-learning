package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FeatureSchemaVersionSupersededEvent(
    val featureSchemaId: UUID,
    val supersededByFeatureSchemaId: UUID,
    @EventTag(key = "featureDomain")
    val featureDomain: String,
    val supersededVersion: String,
    val supersessionReason: String?,
    @EventTag(key = "version")
    val version: String
)
