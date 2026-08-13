package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class AgentLocalModelUpdateSubmittedEvent(
    val modelUpdateSubmissionId: UUID,
    val executionSessionId: UUID,
    @EventTag(key = "executionPlanId")
    val executionPlanId: UUID,
    val roundExecutionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val localModelId: UUID,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val trainingLoss: BigDecimal
)
