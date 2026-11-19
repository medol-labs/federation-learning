package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class ModelUpdateSubmissionRejectedEvent(
    val modelUpdateSubmissionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    @EventTag(key = "roundId")
    val roundId: UUID,
    val runtimeId: UUID,
    val updateArtifactId: UUID,
    val anomalyScore: BigDecimal,
    val rejectionReason: String
)
