package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class ModelUpdateSubmissionReceivedEvent(
    val modelUpdateSubmissionId: UUID,
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val roundExecutionId: UUID,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val localModelId: UUID,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val trainingLoss: BigDecimal
)
