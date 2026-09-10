package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingJobCreatedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val federationId: UUID,
    val federationName: String?,
    val initialModelId: UUID,
    val featureSchemaId: UUID,
    val trainingRunConfigurationId: UUID,
    val configurationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val trainingJobObjective: String,
    val objective: String
)
