package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class FeatureSchemaRetiredEvent(
    val featureSchemaId: UUID,
    val retirementReason: String,
    @EventTag(key = "featureDomain")
    val featureDomain: String,
    @EventTag(key = "version")
    val version: String
)
