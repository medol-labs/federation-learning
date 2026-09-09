package tech.medo.secureaggregation.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class EncryptedModelUpdateReceivedEvent(
    @EventTag(key = "secureAggregationSessionId")
    val secureAggregationSessionId: UUID,
    val submissionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val runtimeId: UUID,
    val updateArtifactId: UUID,
    val encryptedUpdateArtifactRef: String,
    val encryptedUpdateDigest: String,
    val encryptionScheme: String,
    val publicKeyVersion: String,
    val receivedEncryptedUpdateCount: Int,
    val receivedRuntimeIds: List<UUID>,
    val receivedEncryptedUpdateArtifactRefs: List<String>,
    val selectedParticipantCount: Int
)
