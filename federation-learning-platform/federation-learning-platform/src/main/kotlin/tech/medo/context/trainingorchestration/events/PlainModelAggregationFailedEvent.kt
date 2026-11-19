package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class PlainModelAggregationFailedEvent(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    @EventTag(key = "roundId")
    val roundId: UUID,
    val roundNumber: Int,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val failureReason: String
)
