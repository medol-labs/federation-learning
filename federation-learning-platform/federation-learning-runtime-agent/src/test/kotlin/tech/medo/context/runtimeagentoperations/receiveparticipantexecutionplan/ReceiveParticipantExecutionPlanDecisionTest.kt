package tech.medo.runtimeagentoperations.receiveparticipantexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.receiveparticipantexecutionplan.ReceiveParticipantExecutionPlanCommand
import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import java.util.UUID
import java.math.BigDecimal

class ReceiveParticipantExecutionPlanDecisionTest {
    @Test
    fun ReceiveParticipantExecutionPlanEmitsExecutionPlanReceivedEvent() {
        val events = (object : ReceiveParticipantExecutionPlanDecision {}).decide(
            ReceiveParticipantExecutionPlanCommand(
            executionPlanId = java.util.UUID.randomUUID(),
            roundExecutionId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
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
            encryptedParameterScale = null
            )
        )

        assertTrue(events.any { it is ExecutionPlanReceivedEvent })
    }
}
