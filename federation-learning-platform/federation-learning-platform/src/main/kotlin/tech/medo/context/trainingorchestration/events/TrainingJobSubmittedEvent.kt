package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingJobSubmittedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val federationId: UUID,
    val federationName: String?,
    val configurationName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val trainingJobObjective: String,
    val objective: String
)
