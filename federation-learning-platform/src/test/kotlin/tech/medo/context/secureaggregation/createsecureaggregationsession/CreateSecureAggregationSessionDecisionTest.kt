package tech.medo.secureaggregation.createsecureaggregationsession

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.secureaggregation.createsecureaggregationsession.CreateSecureAggregationSessionCommand
import tech.medo.secureaggregation.events.SecureAggregationSessionCreatedEvent
import java.util.UUID

class CreateSecureAggregationSessionDecisionTest {
    @Test
    fun CreateSecureAggregationSessionEmitsSecureAggregationSessionCreatedEvent() {
        val events = (object : CreateSecureAggregationSessionDecision {}).decide(
            CreateSecureAggregationSessionCommand(
            secureAggregationSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            requiredParticipantCount = 0,
            acceptedRuntimeIds = emptyList()
            )
        )

        assertTrue(events.any { it is SecureAggregationSessionCreatedEvent })
    }
}
