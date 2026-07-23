package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


/* TODO: provide values for selection tags: featureDomain, version */

@Event
data class FeatureSchemaDeprecatedEvent(
    val featureSchemaId: UUID,
    val deprecationReason: String
)
