package tech.medo.secureaggregation.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class SecureAggregationCompletedEvent(
    @EventTag(key = "secureAggregationSessionId")
    val secureAggregationSessionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val aggregatedModelVersionId: UUID,
    val modelFormat: String,
    val modelHash: String
)
