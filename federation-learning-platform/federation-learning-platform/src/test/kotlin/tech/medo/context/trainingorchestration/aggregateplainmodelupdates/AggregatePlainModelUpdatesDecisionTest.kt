package tech.medo.trainingorchestration.aggregateplainmodelupdates

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesCommand
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.PlainModelAggregationCompletedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesResult
import java.util.UUID
import java.math.BigDecimal

class AggregatePlainModelUpdatesDecisionTest {
    @Test
    fun AggregateAcceptedPlainUpdates() {
        val state = TrainingRoundState()
        state.evolve(
            ModelUpdateSubmissionAcceptedEvent(
            modelUpdateSubmissionId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            trainingJobObjective = "",
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            maxRounds = 0,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            aggregationAlgorithm = null,
            runtimeId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            updateArtifactId = java.util.UUID.randomUUID(),
            artifactRef = "",
            artifactDigest = "",
            updateProtectionType = "",
            anomalyScore = java.math.BigDecimal.ZERO,
            acceptedModelUpdateCount = 1,
            acceptedRuntimeIds = emptyList(),
            acceptedModelUpdateArtifactRefs = emptyList(),
            minimumNodesPerRound = 1,
            requiredModelUpdateCount = 0,
            plainAggregationReady = false
            )
        )

        val command = AggregatePlainModelUpdatesCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            trainingJobObjective = "",
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            maxRounds = 0,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            modelPlugin = "",
            aggregationAlgorithm = "",
            aggregatedModelId = java.util.UUID.randomUUID(),
            modelUpdateArtifactRefs = emptyList()
        )

        val events = (object : AggregatePlainModelUpdatesDecision {}).decide(
            command,
            state = state,
            portResult = AggregatePlainModelUpdatesResult.Succeeded(
                aggregatedModelName = "",
                aggregatedModelVersion = "",
                aggregatedModelDescription = null,
                modelSourceType = "",
                aggregatedModelArtifactUri = "",
                aggregatedModelRegistryRef = "",
                modelFormat = "",
                modelArtifactDigest = "",
                aggregatedModelSignatureUri = null,
                aggregatedModelSizeBytes = null
            )
        )

        val event = events.filterIsInstance<PlainModelAggregationCompletedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("round-1".toByteArray()), event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.maxRounds, event.maxRounds)
        assertEquals(command.minimumAccuracy, event.minimumAccuracy)
        assertEquals(command.modelPlugin, event.modelPlugin)
        assertEquals(command.aggregatedModelId, event.aggregatedModelId)
    }
}
