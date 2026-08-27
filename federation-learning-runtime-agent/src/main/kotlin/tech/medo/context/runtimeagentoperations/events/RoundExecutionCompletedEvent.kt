package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class RoundExecutionCompletedEvent(
    val roundExecutionId: UUID,
    val executionSessionId: UUID,
    @EventTag(key = "executionPlanId")
    val executionPlanId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val runtimeEngineJobId: String,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val localUpdateArtifactRef: String?,
    val encryptedUpdateArtifactRef: String?,
    val encryptedUpdateDigest: String?,
    val modelUpdateArtifactRef: String?,
    val modelUpdateArtifactDigest: String?,
    val updateProtectionType: String?,
    val metricsArtifactRef: String?,
    val trainingLoss: BigDecimal?
)
