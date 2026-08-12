package tech.medo.trainingorchestration.selecttrainingroundparticipants

import java.util.UUID

interface SelectTrainingRoundParticipantsService {
    fun supports(input: SelectTrainingRoundParticipantsInput): Boolean = true
    fun execute(input: SelectTrainingRoundParticipantsInput): SelectTrainingRoundParticipantsResult
}

data class SelectTrainingRoundParticipantsInput(
    val trainingJobId: UUID
)

sealed interface SelectTrainingRoundParticipantsResult {
    data class Succeeded(
        val trainingRunConfigurationId: UUID,
        val federationId: UUID,
        val featureSchemaId: UUID,
        val currentRoundNumber: Int?,
        val minimumNodesPerRound: Int,
        val memberships: List<FederationMembershipSnapshot>,
        val runtimeIdentities: List<RuntimeIdentitySnapshot>,
        val runtimeInfrastructureAccesses: List<RuntimeInfrastructureAccessSnapshot>,
        val datasetMetadata: List<RuntimeDatasetMetadataSnapshot>
    ) : SelectTrainingRoundParticipantsResult
}

data class FederationMembershipSnapshot(
    val federationId: UUID?,
    val organizationId: UUID?,
    val membershipStatus: String?
)

data class RuntimeIdentitySnapshot(
    val runtimeId: UUID?,
    val runtimeAgentId: UUID?,
    val organizationId: UUID?,
    val identityStatus: String?
)

data class RuntimeInfrastructureAccessSnapshot(
    val runtimeAgentId: UUID?,
    val state: String?
)

data class RuntimeDatasetMetadataSnapshot(
    val datasetId: UUID?,
    val organizationId: UUID?,
    val runtimeId: UUID?,
    val featureSchemaId: UUID?,
    val schemaCompatible: Boolean?,
    val labelCompatible: Boolean?
)
