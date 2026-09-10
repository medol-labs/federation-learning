package tech.medo.secureaggregation.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class SecureAggregationSessionCreatedEvent(
    @EventTag(key = "secureAggregationSessionId")
    val secureAggregationSessionId: UUID,
    val trainingJobId: UUID,
    val federationId: UUID,
    val federationName: String?,
    val trainingRunConfigurationId: UUID,
    val configurationName: String?,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val roundId: UUID,
    val roundNumber: Int,
    val requiredParticipantCount: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val secureAggregationRequired: Boolean
)
