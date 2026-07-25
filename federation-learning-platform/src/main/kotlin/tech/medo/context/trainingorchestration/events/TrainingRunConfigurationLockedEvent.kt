package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class TrainingRunConfigurationLockedEvent(
    @EventTag(key = "trainingRunConfigurationId")
    val trainingRunConfigurationId: UUID,
    val trainingJobId: UUID
)
