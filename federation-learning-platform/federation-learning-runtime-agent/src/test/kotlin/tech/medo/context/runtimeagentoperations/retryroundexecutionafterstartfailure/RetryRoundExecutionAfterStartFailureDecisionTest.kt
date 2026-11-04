package tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureCommand
import tech.medo.runtimeagentoperations.events.RoundExecutionStartFailedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartRetryFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureResult
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

class RetryRoundExecutionAfterStartFailureDecisionTest {
    @Test
    fun Retryfails() {
        val state = RoundExecutionState()
        state.evolve(
            RoundExecutionStartFailedEvent(
            roundExecutionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            personalizationMode = null,
            personalizationScope = null,
            personalizedArtifactPolicy = null,
            personalizationMetric = null,
            minimumPersonalizationLift = null,
            personalizationTargetType = null,
            personalizationTargetId = null,
            personalizationCohortId = null,
            personalizationProfileId = null,
            baseModelId = java.util.UUID.randomUUID(),
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelPlugin = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null,
            personalizedBaseModelId = null,
            personalizedBaseModelArtifactUri = null,
            personalizedBaseModelArtifactDigest = null,
            runtimeEngineProfileId = java.util.UUID.randomUUID(),
            runtimeEngineProfileName = null,
            runtimeEnginePluginProfile = "",
            runtimeEngineImage = "",
            runtimeEngineImageDigest = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null,
            runtimeEngineJobId = null,
            failureReason = ""
            )
        )

        val command = RetryRoundExecutionAfterStartFailureCommand(
            roundExecutionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            baseModelId = java.util.UUID.randomUUID(),
            runtimeEngineJobId = "",
            retryReason = ""
        )

        val events = (object : RetryRoundExecutionAfterStartFailureDecision {}).decide(
            command,
            state = state,
            portResult = RetryRoundExecutionAfterStartFailureResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RoundExecutionStartRetryFailedEvent>().single()
        assertEquals(UUID.fromString("11111111-1111-4111-8111-111111111111"), event.roundExecutionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.baseModelId, event.baseModelId)
        assertEquals(command.runtimeEngineJobId, event.runtimeEngineJobId)
        assertEquals(command.retryReason, event.retryReason)
    }
}
