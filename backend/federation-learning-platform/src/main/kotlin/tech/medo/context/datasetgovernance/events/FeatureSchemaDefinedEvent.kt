package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;



@Event
data class FeatureSchemaDefinedEvent(
    val featureSchemaId: UUID,
    @EventTag(key = "featureDomain")
    val featureDomain: String,
    @EventTag(key = "version")
    val version: String,
    val dataModality: String,
    val features: List<FeatureDefinition>,
    val labels: List<LabelDefinition>,
    val featureCount: Int
)
