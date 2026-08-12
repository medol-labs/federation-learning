package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.domain.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsDecisionComponent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent

import java.util.UUID


class SelectTrainingRoundParticipantsDecisionTest {
    private val decision = SelectTrainingRoundParticipantsDecisionComponent()

    @Test
    fun SelectParticipantsWhenRuntimePoolReachesQuorum() {
        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray())
        )
        val federationId = UUID.nameUUIDFromBytes("federation-1".toByteArray())
        val featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray())
        val trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray())
        val organizationId = UUID.nameUUIDFromBytes("org-1".toByteArray())
        val runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray())
        val runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray())
        val datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray())

        val events = decision.decide(
            command = command,
            portResult = SelectTrainingRoundParticipantsResult.Succeeded(
                trainingRunConfigurationId = trainingRunConfigurationId,
                federationId = federationId,
                featureSchemaId = featureSchemaId,
                currentRoundNumber = 0,
                minimumNodesPerRound = 1,
                memberships = listOf(
                    FederationMembershipSnapshot(
                        federationId = federationId,
                        organizationId = organizationId,
                        membershipStatus = "Joined"
                    )
                ),
                runtimeIdentities = listOf(
                    RuntimeIdentitySnapshot(
                        runtimeId = runtimeId,
                        runtimeAgentId = runtimeAgentId,
                        organizationId = organizationId,
                        identityStatus = "Active"
                    )
                ),
                runtimeInfrastructureAccesses = listOf(
                    RuntimeInfrastructureAccessSnapshot(
                        runtimeAgentId = runtimeAgentId,
                        state = "CONNECTED"
                    )
                ),
                datasetMetadata = listOf(
                    RuntimeDatasetMetadataSnapshot(
                        datasetId = datasetId,
                        organizationId = organizationId,
                        runtimeId = runtimeId,
                        featureSchemaId = featureSchemaId,
                        schemaCompatible = true,
                        labelCompatible = true
                    )
                )
            )
        )

        val event = events.filterIsInstance<TrainingRoundParticipantsSelectedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(featureSchemaId, event.featureSchemaId)
        assertEquals(1, event.minimumNodesPerRound)
        assertEquals(1, event.roundNumber)
        assertEquals(1, event.selectedRuntimeCount)
        assertEquals(listOf(organizationId), event.selectedOrganizationIds)
        assertEquals(listOf(runtimeId), event.selectedRuntimeIds)
        assertEquals(organizationId, event.selectedParticipants.single().organizationId)
        assertEquals(runtimeId, event.selectedParticipants.single().runtimeId)
        assertEquals(datasetId, event.selectedParticipants.single().datasetId)
    }

    @Test
    fun DoesNotEmitParticipantsWhenSelectionIsBelowQuorum() {
        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-2".toByteArray())
        )
        val featureSchemaId = UUID.nameUUIDFromBytes("schema-2".toByteArray())
        val federationId = UUID.nameUUIDFromBytes("federation-2".toByteArray())
        val trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-2".toByteArray())
        val organizationId = UUID.nameUUIDFromBytes("org-2".toByteArray())
        val runtimeId = UUID.nameUUIDFromBytes("runtime-2".toByteArray())
        val runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-2".toByteArray())
        val datasetId = UUID.nameUUIDFromBytes("dataset-2".toByteArray())

        val events = decision.decide(
            command = command,
            portResult = SelectTrainingRoundParticipantsResult.Succeeded(
                trainingRunConfigurationId = trainingRunConfigurationId,
                federationId = federationId,
                featureSchemaId = featureSchemaId,
                currentRoundNumber = 0,
                minimumNodesPerRound = 2,
                memberships = listOf(
                    FederationMembershipSnapshot(
                        federationId = federationId,
                        organizationId = organizationId,
                        membershipStatus = "Joined"
                    )
                ),
                runtimeIdentities = listOf(
                    RuntimeIdentitySnapshot(
                        runtimeId = runtimeId,
                        runtimeAgentId = runtimeAgentId,
                        organizationId = organizationId,
                        identityStatus = "Active"
                    )
                ),
                runtimeInfrastructureAccesses = listOf(
                    RuntimeInfrastructureAccessSnapshot(
                        runtimeAgentId = runtimeAgentId,
                        state = "CONNECTED"
                    )
                ),
                datasetMetadata = listOf(
                    RuntimeDatasetMetadataSnapshot(
                        datasetId = datasetId,
                        organizationId = organizationId,
                        runtimeId = runtimeId,
                        featureSchemaId = featureSchemaId,
                        schemaCompatible = true,
                        labelCompatible = true
                    )
                )
            )
        )

        assertTrue(events.isEmpty())
    }
}
