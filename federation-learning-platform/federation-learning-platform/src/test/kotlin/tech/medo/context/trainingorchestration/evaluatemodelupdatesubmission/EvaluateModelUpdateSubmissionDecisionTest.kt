package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import java.util.UUID
import java.math.BigDecimal
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant

class EvaluateModelUpdateSubmissionDecisionTest {
    @Test
    fun AcceptNormalUpdate() {
        val state = TrainingRoundState()
        state.evolve(
            TrainingRoundStartedEvent(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            configurationName = null,
            trainingJobObjective = "",
            featureSchemaId = java.util.UUID.randomUUID(),
            featureDomain = null,
            featureSchemaVersion = null,
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            selectedOrganizationIds = emptyList(),
            selectedRuntimeIds = emptyList(),
            selectedParticipants = emptyList(),
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 2,
            minimumNodesPerRound = 2,
            maxRounds = 0,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            aggregationAlgorithm = null,
            personalizationMode = null,
            personalizationScope = null,
            personalizedArtifactPolicy = null,
            personalizationMetric = null,
            minimumPersonalizationLift = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null
            )
        )
        state.evolve(
            ModelUpdateSubmissionReceivedEvent(
            modelUpdateSubmissionId = UUID.nameUUIDFromBytes("submission-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundExecutionId = java.util.UUID.randomUUID(),
            runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray()),
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
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            localModelId = java.util.UUID.randomUUID(),
            updateArtifactId = UUID.nameUUIDFromBytes("artifact-1".toByteArray()),
            artifactRef = "",
            artifactDigest = "",
            updateProtectionType = "",
            trainingLoss = BigDecimal("0.23"),
            personalizedArtifactAvailable = false,
            personalizedModelId = null,
            personalizedModelArtifactRef = null,
            personalizedModelArtifactDigest = null,
            personalizedMetricsArtifactRef = null,
            personalizedAccuracy = null,
            personalizationLift = null
            )
        )

        val command = EvaluateModelUpdateSubmissionCommand(
            modelUpdateSubmissionId = UUID.nameUUIDFromBytes("submission-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray()),
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
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            updateArtifactId = java.util.UUID.randomUUID(),
            artifactRef = "",
            artifactDigest = "",
            updateProtectionType = "",
            personalizedArtifactAvailable = false,
            personalizedModelId = null,
            personalizedModelArtifactRef = null,
            personalizedModelArtifactDigest = null,
            personalizedMetricsArtifactRef = null,
            personalizedAccuracy = null,
            personalizationLift = null,
            anomalyScore = BigDecimal("0.12")
        )

        val events = (object : EvaluateModelUpdateSubmissionDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<ModelUpdateSubmissionAcceptedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("submission-1".toByteArray()), event.modelUpdateSubmissionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(UUID.nameUUIDFromBytes("runtime-1".toByteArray()), event.runtimeId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.personalizationMode, event.personalizationMode)
        assertEquals(command.personalizationScope, event.personalizationScope)
        assertEquals(command.personalizedArtifactPolicy, event.personalizedArtifactPolicy)
        assertEquals(command.personalizationMetric, event.personalizationMetric)
        assertEquals(command.minimumPersonalizationLift, event.minimumPersonalizationLift)
        assertEquals(command.personalizationTargetType, event.personalizationTargetType)
        assertEquals(command.personalizationTargetId, event.personalizationTargetId)
        assertEquals(command.personalizationCohortId, event.personalizationCohortId)
        assertEquals(command.personalizationProfileId, event.personalizationProfileId)
        assertEquals(command.secureAggregationRequired, event.secureAggregationRequired)
        assertEquals(command.secureAggregationSessionId, event.secureAggregationSessionId)
        assertEquals(command.encryptionScheme, event.encryptionScheme)
        assertEquals(command.publicKeyVersion, event.publicKeyVersion)
        assertEquals(command.updateArtifactId, event.updateArtifactId)
        assertEquals(command.artifactRef, event.artifactRef)
        assertEquals(command.artifactDigest, event.artifactDigest)
        assertEquals(command.updateProtectionType, event.updateProtectionType)
        assertEquals(command.personalizedArtifactAvailable, event.personalizedArtifactAvailable)
        assertEquals(command.personalizedModelId, event.personalizedModelId)
        assertEquals(command.personalizedModelArtifactRef, event.personalizedModelArtifactRef)
        assertEquals(command.personalizedModelArtifactDigest, event.personalizedModelArtifactDigest)
        assertEquals(command.personalizedMetricsArtifactRef, event.personalizedMetricsArtifactRef)
        assertEquals(command.personalizedAccuracy, event.personalizedAccuracy)
        assertEquals(command.personalizationLift, event.personalizationLift)
        assertEquals(BigDecimal("0.12"), event.anomalyScore)
    }
}
