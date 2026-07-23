package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


@Event
data class FeatureSchemaFeatureDomainVersionReservedEvent(
    val featureSchemaId: UUID,
    val featureDomain: String,
    val version: String,
    @EventTag(key = "featureDomain")
    val normalizedFeatureDomain: String,
    @EventTag(key = "version")
    val normalizedVersion: String
)
