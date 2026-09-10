package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition;
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition;



@Event
data class DatasetDeclaredEvent(
    val datasetId: UUID,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    val organizationName: String?,
    @EventTag(key = "featureSchemaId")
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    @EventTag(key = "datasetName")
    val datasetName: String,
    val datasetUsage: String,
    val features: List<FeatureDefinition>,
    val labels: List<LabelDefinition>
)
