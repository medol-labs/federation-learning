package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class ModelUpdateSubmissionAcceptedEvent(
    val modelUpdateSubmissionId: UUID,
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregationAlgorithm: String?,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val updateProtectionType: String,
    val anomalyScore: BigDecimal,
    val acceptedModelUpdateCount: Int,
    val acceptedRuntimeIds: List<UUID>,
    val acceptedModelUpdateArtifactRefs: List<String>,
    val minimumNodesPerRound: Int,
    val requiredModelUpdateCount: Int,
    val plainAggregationReady: Boolean
)
