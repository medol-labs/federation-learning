package tech.medo.secureaggregation.createsecureaggregationsession

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.secureaggregation.createsecureaggregationsession.CreateSecureAggregationSessionCommand
import tech.medo.secureaggregation.events.SecureAggregationSessionCreatedEvent
import java.util.UUID
import java.math.BigDecimal

class CreateSecureAggregationSessionDecisionTest {
    @Test
    fun CreateSecureAggregationSessionEmitsSecureAggregationSessionCreatedEvent() {
        val events = (object : CreateSecureAggregationSessionDecision {}).decide(
            CreateSecureAggregationSessionCommand(
            secureAggregationSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            configurationName = null,
            trainingJobObjective = "",
            featureSchemaId = java.util.UUID.randomUUID(),
            featureDomain = null,
            featureSchemaVersion = null,
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            requiredParticipantCount = 0,
            selectedOrganizationIds = emptyList(),
            selectedRuntimeIds = emptyList(),
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 0,
            minimumNodesPerRound = 0,
            maxRounds = 0,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            secureAggregationRequired = false
            )
        )

        assertTrue(events.any { it is SecureAggregationSessionCreatedEvent })
    }
}
