package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FeatureSchemaPublishedEvent(
    val featureSchemaId: UUID,
    val publishNote: String?,
    @EventTag(key = "featureDomain")
    val featureDomain: String,
    @EventTag(key = "version")
    val version: String
)
