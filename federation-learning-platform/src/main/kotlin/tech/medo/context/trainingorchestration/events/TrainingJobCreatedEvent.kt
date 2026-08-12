package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingJobCreatedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val federationId: UUID,
    val featureSchemaId: UUID,
    val trainingRunConfigurationId: UUID,
    val objective: String,
    val minimumNodesPerRound: Int? = null
)
