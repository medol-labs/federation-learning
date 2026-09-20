package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionFailedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import java.util.UUID
import java.math.BigDecimal
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant
import java.time.LocalDateTime

class SelectTrainingRoundParticipantsDecisionTest {
    @Test
    fun SelectParticipantsWhenRuntimePoolReachesQuorum() {


        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            configurationName = null,
            featureSchemaId = java.util.UUID.randomUUID(),
            featureDomain = null,
            featureSchemaVersion = null,
            trainingJobObjective = ""
        )

        val events = (object : SelectTrainingRoundParticipantsDecision {}).decide(
            command,
            portResult = SelectTrainingRoundParticipantsResult.Succeeded(
                roundId = java.util.UUID.randomUUID(),
                roundNumber = 0,
                maxRounds = 0,
                minimumAccuracy = java.math.BigDecimal.ZERO,
                aggregationAlgorithm = "",
                minimumNodesPerRound = 0,
                secureAggregationRequired = false,
                selectedOrganizationIds = emptyList(),
                selectedRuntimeIds = emptyList(),
                selectedParticipants = emptyList(),
                selectedOrganizationCount = 0,
                selectedRuntimeCount = 0
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundParticipantsSelectedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(command.federationName, event.federationName)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.configurationName, event.configurationName)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
    }

    @Test
    fun RejectParticipantSnapshotBelowQuorum() {


        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            configurationName = null,
            featureSchemaId = java.util.UUID.randomUUID(),
            featureDomain = null,
            featureSchemaVersion = null,
            trainingJobObjective = ""
        )

        val events = (object : SelectTrainingRoundParticipantsDecision {}).decide(
            command,
            portResult = SelectTrainingRoundParticipantsResult.Rejected(
                roundId = java.util.UUID.randomUUID(),
                roundNumber = 0,
                maxRounds = 0,
                minimumAccuracy = java.math.BigDecimal.ZERO,
                aggregationAlgorithm = "",
                minimumNodesPerRound = 0,
                secureAggregationRequired = false,
                selectedOrganizationIds = emptyList(),
                selectedRuntimeIds = emptyList(),
                selectedParticipants = emptyList(),
                selectedOrganizationCount = 0,
                selectedRuntimeCount = 0,
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundParticipantSelectionFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(command.federationName, event.federationName)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.configurationName, event.configurationName)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
    }

    @Test
    fun RejectSelectionWithoutJoinedFederationMembers() {


        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            configurationName = null,
            featureSchemaId = java.util.UUID.randomUUID(),
            featureDomain = null,
            featureSchemaVersion = null,
            trainingJobObjective = ""
        )

        val events = (object : SelectTrainingRoundParticipantsDecision {}).decide(
            command,
            portResult = SelectTrainingRoundParticipantsResult.Rejected(
                roundId = java.util.UUID.randomUUID(),
                roundNumber = 0,
                maxRounds = 0,
                minimumAccuracy = java.math.BigDecimal.ZERO,
                aggregationAlgorithm = "",
                minimumNodesPerRound = 0,
                secureAggregationRequired = false,
                selectedOrganizationIds = emptyList(),
                selectedRuntimeIds = emptyList(),
                selectedParticipants = emptyList(),
                selectedOrganizationCount = 0,
                selectedRuntimeCount = 0,
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundParticipantSelectionFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(command.federationName, event.federationName)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.configurationName, event.configurationName)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
    }

    @Test
    fun RejectSelectionWithoutCompatibleDatasetEvidence() {


        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            configurationName = null,
            featureSchemaId = java.util.UUID.randomUUID(),
            featureDomain = null,
            featureSchemaVersion = null,
            trainingJobObjective = ""
        )

        val events = (object : SelectTrainingRoundParticipantsDecision {}).decide(
            command,
            portResult = SelectTrainingRoundParticipantsResult.Rejected(
                roundId = java.util.UUID.randomUUID(),
                roundNumber = 0,
                maxRounds = 0,
                minimumAccuracy = java.math.BigDecimal.ZERO,
                aggregationAlgorithm = "",
                minimumNodesPerRound = 0,
                secureAggregationRequired = false,
                selectedOrganizationIds = emptyList(),
                selectedRuntimeIds = emptyList(),
                selectedParticipants = emptyList(),
                selectedOrganizationCount = 0,
                selectedRuntimeCount = 0,
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<TrainingRoundParticipantSelectionFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(command.federationName, event.federationName)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.configurationName, event.configurationName)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.featureDomain, event.featureDomain)
        assertEquals(command.featureSchemaVersion, event.featureSchemaVersion)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
    }
}
