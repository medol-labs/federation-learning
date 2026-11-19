package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingRoundFailedEvent(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    @EventTag(key = "roundId")
    val roundId: UUID,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val failureReason: String
)
