package tech.medo.trainingorchestration.starttraininground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartFailedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import java.util.UUID
import java.math.BigDecimal
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant
import java.time.LocalDateTime

class StartTrainingRoundDecisionTest {
    @Test
    fun StartRoundWithSelectedRuntimeQuorum() {
        val state = TrainingRoundState()


        val command = StartTrainingRoundCommand(
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
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 3,
            minimumNodesPerRound = 0,
            maxRounds = 0,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            aggregationAlgorithm = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null
        )

        val events = (object : StartTrainingRoundDecision {}).decide(
            command,
            state = state,
            portResult = StartTrainingRoundResult.Succeeded(

            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundStartedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(command.federationName, event.federationName)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.configurationName, event.configurationName)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(UUID.nameUUIDFromBytes("round-1".toByteArray()), event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.selectedOrganizationIds, event.selectedOrganizationIds)
        assertEquals(command.selectedRuntimeIds, event.selectedRuntimeIds)
        assertEquals(command.selectedOrganizationCount, event.selectedOrganizationCount)
        assertEquals(3, event.selectedRuntimeCount)
        assertEquals(command.minimumNodesPerRound, event.minimumNodesPerRound)
        assertEquals(command.maxRounds, event.maxRounds)
        assertEquals(command.minimumAccuracy, event.minimumAccuracy)
        assertEquals(command.aggregationAlgorithm, event.aggregationAlgorithm)
        assertEquals(command.secureAggregationRequired, event.secureAggregationRequired)
        assertEquals(command.secureAggregationSessionId, event.secureAggregationSessionId)
        assertEquals(command.encryptionScheme, event.encryptionScheme)
        assertEquals(command.publicKeyVersion, event.publicKeyVersion)
        assertEquals(command.publicKeyRef, event.publicKeyRef)
        assertEquals(command.encryptedParameterScale, event.encryptedParameterScale)
    }

    @Test
    fun RecordFailedRoundStartBelowQuorum() {
        val state = TrainingRoundState()


        val command = StartTrainingRoundCommand(
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
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 2,
            minimumNodesPerRound = 0,
            maxRounds = 0,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            aggregationAlgorithm = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null
        )

        val events = (object : StartTrainingRoundDecision {}).decide(
            command,
            state = state,
            portResult = StartTrainingRoundResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundStartFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(command.federationName, event.federationName)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.configurationName, event.configurationName)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(UUID.nameUUIDFromBytes("round-1".toByteArray()), event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.selectedOrganizationIds, event.selectedOrganizationIds)
        assertEquals(command.selectedRuntimeIds, event.selectedRuntimeIds)
        assertEquals(command.selectedOrganizationCount, event.selectedOrganizationCount)
        assertEquals(2, event.selectedRuntimeCount)
        assertEquals(command.minimumNodesPerRound, event.minimumNodesPerRound)
        assertEquals(command.maxRounds, event.maxRounds)
        assertEquals(command.minimumAccuracy, event.minimumAccuracy)
        assertEquals(command.secureAggregationRequired, event.secureAggregationRequired)
        assertEquals(command.secureAggregationSessionId, event.secureAggregationSessionId)
        assertEquals(command.encryptionScheme, event.encryptionScheme)
        assertEquals(command.publicKeyVersion, event.publicKeyVersion)
        assertEquals(command.publicKeyRef, event.publicKeyRef)
        assertEquals(command.encryptedParameterScale, event.encryptedParameterScale)
    }
}
