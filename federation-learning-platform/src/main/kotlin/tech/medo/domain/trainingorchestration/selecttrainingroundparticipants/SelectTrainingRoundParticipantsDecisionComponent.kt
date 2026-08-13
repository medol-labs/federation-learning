package tech.medo.domain.trainingorchestration.selecttrainingroundparticipants

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.RuntimeDatasetMetadataSnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.RuntimeIdentitySnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsDecision
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import java.util.UUID

@Component
class SelectTrainingRoundParticipantsDecisionComponent : SelectTrainingRoundParticipantsDecision {
    private val log = LoggerFactory.getLogger(SelectTrainingRoundParticipantsDecisionComponent::class.java)
    private val objectMapper = ObjectMapper()

    override fun decide(
        command: SelectTrainingRoundParticipantsCommand,
        portResult: SelectTrainingRoundParticipantsResult
    ): List<Any> =
        when (portResult) {
            is SelectTrainingRoundParticipantsResult.Succeeded -> decideSucceeded(command, portResult)
        }

    private fun decideSucceeded(
        command: SelectTrainingRoundParticipantsCommand,
        snapshot: SelectTrainingRoundParticipantsResult.Succeeded
    ): List<Any> {
        require(snapshot.minimumNodesPerRound > 0) {
            "TrainingRunConfiguration.minimumNodesPerRound must be greater than zero."
        }

        val joinedOrganizationIds = snapshot.memberships
            .filter { it.federationId == snapshot.federationId }
            .filter { it.organizationId != null }
            .filter { it.membershipStatus.isJoinedLike() }
            .mapNotNull { it.organizationId }
            .toSet()

        val connectedRuntimeAgentIds = snapshot.runtimeInfrastructureAccesses
            .filter { it.state.isRuntimeSelectable() }
            .mapNotNull { it.runtimeAgentId }
            .toSet()

        val activeRuntimes = snapshot.runtimeIdentities
            .filter { it.runtimeId != null && it.organizationId != null }
            .filter { it.organizationId in joinedOrganizationIds }
            .filter { it.identityStatus.isActiveLike() }
            .filter { connectedRuntimeAgentIds.isEmpty() || it.runtimeAgentId in connectedRuntimeAgentIds }
            .associateBy { it.runtimeId!! }

        val activeRuntimeByOrganization = activeRuntimes.values
            .groupBy { it.organizationId!! }
            .mapValues { (_, runtimes) -> runtimes.first() }

        val matchingDatasetMetadata = snapshot.datasetMetadata
            .filter { it.featureSchemaId == snapshot.featureSchemaId }
            // TODO
            // .filter { it.schemaCompatible == true && it.labelCompatible == true }
            .filter { it.organizationId in joinedOrganizationIds }
            .filter { it.datasetId != null }

        log.info(
            "Matched runtime dataset metadata for participant selection. trainingJobId={}, federationId={}, featureSchemaId={}, matchingDatasetMetadataCount={}, matchingDatasetMetadata={}",
            command.trainingJobId,
            snapshot.federationId,
            snapshot.featureSchemaId,
            matchingDatasetMetadata.size,
            objectMapper.writeValueAsString(
                matchingDatasetMetadata.map {
                    mapOf(
                        "datasetId" to it.datasetId,
                        "organizationId" to it.organizationId,
                        "runtimeId" to it.runtimeId,
                        "featureSchemaId" to it.featureSchemaId,
                        "schemaCompatible" to it.schemaCompatible,
                        "labelCompatible" to it.labelCompatible
                    )
                }
            )
        )

        val selectedParticipants = matchingDatasetMetadata
            .mapNotNull { metadata ->
                val runtime = activeRuntimes[metadata.runtimeId]
                    ?: metadata.organizationId?.let { activeRuntimeByOrganization[it] }
                metadata.toParticipant(runtime)
            }
            .distinctBy { it.runtimeId }

        if (selectedParticipants.size < snapshot.minimumNodesPerRound) {
            log.warn(
                "Skip training round participant selection because selected runtime count is below quorum. trainingJobId={}, federationId={}, featureSchemaId={}, selectedRuntimeCount={}, minimumNodesPerRound={}, joinedOrganizationCount={}, connectedRuntimeAgentCount={}, activeRuntimeCount={}, matchingDatasetMetadataCount={}",
                command.trainingJobId,
                snapshot.federationId,
                snapshot.featureSchemaId,
                selectedParticipants.size,
                snapshot.minimumNodesPerRound,
                joinedOrganizationIds.size,
                connectedRuntimeAgentIds.size,
                activeRuntimes.size,
                matchingDatasetMetadata.size
            )
            return emptyList()
        }

        val selectedOrganizationIds = selectedParticipants.map { it.organizationId }.distinct()
        val selectedRuntimeIds = selectedParticipants.map { it.runtimeId }.distinct()

        log.info(
            "Selected training round participants. trainingJobId={}, federationId={}, featureSchemaId={}, selectedRuntimeCount={}, minimumNodesPerRound={}",
            command.trainingJobId,
            snapshot.federationId,
            snapshot.featureSchemaId,
            selectedRuntimeIds.size,
            snapshot.minimumNodesPerRound
        )

        return listOf(
            TrainingRoundParticipantsSelectedEvent(
                trainingJobId = command.trainingJobId,
                trainingRunConfigurationId = snapshot.trainingRunConfigurationId,
                featureSchemaId = snapshot.featureSchemaId,
                roundId = UUID.randomUUID(),
                roundNumber = (snapshot.currentRoundNumber ?: 0) + 1,
                minimumNodesPerRound = snapshot.minimumNodesPerRound,
                selectedOrganizationIds = selectedOrganizationIds,
                selectedRuntimeIds = selectedRuntimeIds,
                selectedParticipants = selectedParticipants,
                selectedOrganizationCount = selectedOrganizationIds.size,
                selectedRuntimeCount = selectedRuntimeIds.size
            )
        )
    }

    private fun RuntimeDatasetMetadataSnapshot.toParticipant(
        runtime: RuntimeIdentitySnapshot?
    ): TrainingRoundParticipant? {
        val organizationId = runtime?.organizationId
        val runtimeId = runtime?.runtimeId
        val datasetId = datasetId

        if (organizationId == null || runtimeId == null || datasetId == null) {
            return null
        }

        return TrainingRoundParticipant(
            organizationId = organizationId,
            runtimeId = runtimeId,
            datasetId = datasetId
        )
    }

    private fun String?.isJoinedLike(): Boolean =
        this == null || equals("Joined", ignoreCase = true) || equals("Approved", ignoreCase = true) || equals("Active", ignoreCase = true)

    private fun String?.isActiveLike(): Boolean =
        this == null || equals("Active", ignoreCase = true)

    private fun String?.isRuntimeSelectable(): Boolean =
        equals("CONNECTED", ignoreCase = true)
}
