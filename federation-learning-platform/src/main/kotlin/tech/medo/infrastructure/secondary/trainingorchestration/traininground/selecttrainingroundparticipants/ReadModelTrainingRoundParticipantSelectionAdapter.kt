package tech.medo.infrastructure.secondary.trainingorchestration.traininground.selecttrainingroundparticipants

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModelRepository
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelRepository
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelRepository
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsInput
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsService
import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import java.util.UUID

@Component
class ReadModelTrainingRoundParticipantSelectionAdapter(
    private val trainingJobDashboard: TrainingJobDashboardReadModelRepository,
    private val trainingRunConfigurationCatalog: TrainingRunConfigurationCatalogReadModelRepository,
    private val membershipDirectory: FederationMembershipDirectoryReadModelRepository,
    private val runtimeIdentityCatalog: RuntimeIdentityCatalogReadModelRepository,
    private val runtimeDatasetMetadataCatalog: RuntimeDatasetMetadataCatalogReadModelRepository
) : SelectTrainingRoundParticipantsService {
    private val log = LoggerFactory.getLogger(ReadModelTrainingRoundParticipantSelectionAdapter::class.java)

    override fun execute(input: SelectTrainingRoundParticipantsInput): SelectTrainingRoundParticipantsResult {
        val trainingJob = trainingJobDashboard.findProjectionById(input.trainingJobId)
        val trainingRunConfiguration = trainingJob?.trainingRunConfigurationId
            ?.let { trainingRunConfigurationCatalog.findProjectionById(it) }
        val trainingRunConfigurationId = trainingJob?.trainingRunConfigurationId
        val federationId = trainingJob?.federationId ?: trainingRunConfiguration?.federationId
        val featureSchemaId = trainingJob?.featureSchemaId ?: trainingRunConfiguration?.featureSchemaId
        val minimumNodesPerRound = trainingRunConfiguration?.minimumNodesPerRound ?: trainingJob?.minimumNodesPerRound

        if (trainingRunConfigurationId == null || federationId == null || featureSchemaId == null || minimumNodesPerRound == null || minimumNodesPerRound <= 0) {
            log.warn(
                "Skip training round participant selection because job context is incomplete. trainingJobId={}, trainingRunConfigurationIdPresent={}, federationIdPresent={}, featureSchemaIdPresent={}, minimumNodesPerRound={}",
                input.trainingJobId,
                trainingRunConfigurationId != null,
                federationId != null,
                featureSchemaId != null,
                minimumNodesPerRound
            )
            error("Training job context is incomplete for participant selection.")
        }

        val joinedOrganizationIds = membershipDirectory.findProjectionsByFederationId(federationId)
            .filter { it.organizationId != null }
            .filter { it.membershipStatus.isJoinedLike() }
            .mapNotNull { it.organizationId }
            .toSet()

        val activeRuntimes = runtimeIdentityCatalog.findAll(Pageable.unpaged()).content
            .filter { it.runtimeId != null && it.organizationId != null }
            .filter { it.organizationId in joinedOrganizationIds }
            .filter { it.identityStatus.isActiveLike() }
            .associateBy { it.runtimeId!! }

        val activeRuntimeByOrganization = activeRuntimes.values
            .groupBy { it.organizationId!! }
            .mapValues { (_, runtimes) -> runtimes.first() }

        val matchingDatasetMetadata = runtimeDatasetMetadataCatalog.findAll(Pageable.unpaged()).content
            .filter { it.featureSchemaId == featureSchemaId }
            // TODO Restore compatibility filtering when dataset contract status is reliably projected.
            // .filter { it.schemaCompatible == true && it.labelCompatible == true }
            .filter { it.organizationId in joinedOrganizationIds }
            .filter { it.datasetId != null }
        log.info("xx {}", matchingDatasetMetadata)
        val selectedParticipants = matchingDatasetMetadata
            .mapNotNull { metadata ->
                val runtime = activeRuntimes[metadata.runtimeId]
                    ?: metadata.organizationId?.let { activeRuntimeByOrganization[it] }
                val organizationId = runtime?.organizationId
                val runtimeId = runtime?.runtimeId
                val datasetId = metadata.datasetId
                if (organizationId == null || runtimeId == null || datasetId == null) {
                    null
                } else {
                    TrainingRoundParticipant(
                        organizationId = organizationId,
                        runtimeId = runtimeId,
                        datasetId = datasetId
                    )
                }
            }
            .distinctBy { it.runtimeId }
        log.info("yy {}", selectedParticipants)
        val selectedOrganizationIds = selectedParticipants.map { it.organizationId }.distinct()
        val selectedRuntimeIds = selectedParticipants.map { it.runtimeId }.distinct()

        log.info(
            "Selected participant candidates from read models. trainingJobId={}, federationId={}, featureSchemaId={}, joinedOrganizationCount={}, activeRuntimeCount={}, matchingDatasetMetadataCount={}, selectedRuntimeCount={}, minimumNodesPerRound={}",
            input.trainingJobId,
            federationId,
            featureSchemaId,
            joinedOrganizationIds.size,
            activeRuntimes.size,
            matchingDatasetMetadata.size,
            selectedRuntimeIds.size,
            minimumNodesPerRound
        )

        return SelectTrainingRoundParticipantsResult.Succeeded(
            trainingRunConfigurationId = trainingRunConfigurationId,
            featureSchemaId = featureSchemaId,
            roundId = UUID.randomUUID(),
            roundNumber = (trainingJob?.currentRoundNumber ?: 0) + 1,
            minimumNodesPerRound = minimumNodesPerRound,
            selectedOrganizationIds = selectedOrganizationIds,
            selectedRuntimeIds = selectedRuntimeIds,
            selectedParticipants = selectedParticipants,
            selectedOrganizationCount = selectedOrganizationIds.size,
            selectedRuntimeCount = selectedRuntimeIds.size
        )
    }

    private fun String?.isJoinedLike(): Boolean =
        this == null || equals("Joined", ignoreCase = true) || equals("Approved", ignoreCase = true) || equals("Active", ignoreCase = true)

    private fun String?.isActiveLike(): Boolean =
        this == null || equals("Active", ignoreCase = true)

}
