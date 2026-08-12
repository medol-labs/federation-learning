package tech.medo.infrastructure.secondary.trainingorchestration.traininground.selecttrainingroundparticipants

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModelRepository
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelRepository
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModelRepository
import tech.medo.trainingorchestration.selecttrainingroundparticipants.FederationMembershipSnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsInput
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsService
import tech.medo.trainingorchestration.selecttrainingroundparticipants.RuntimeDatasetMetadataSnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.RuntimeIdentitySnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.RuntimeInfrastructureAccessSnapshot
import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository

@Component
class ReadModelTrainingRoundParticipantSelectionAdapter(
    private val trainingJobDashboard: TrainingJobDashboardReadModelRepository,
    private val trainingRunConfigurationCatalog: TrainingRunConfigurationCatalogReadModelRepository,
    private val membershipDirectory: FederationMembershipDirectoryReadModelRepository,
    private val runtimeIdentityCatalog: RuntimeIdentityCatalogReadModelRepository,
    private val runtimeInfrastructureAccessView: RuntimeInfrastructureAccessViewReadModelRepository,
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

        val memberships = membershipDirectory.findProjectionsByFederationId(federationId)
            .map {
                FederationMembershipSnapshot(
                    federationId = it.federationId,
                    organizationId = it.organizationId,
                    membershipStatus = it.membershipStatus
                )
            }
        val runtimeIdentities = runtimeIdentityCatalog.findAll(Pageable.unpaged()).content
            .map {
                RuntimeIdentitySnapshot(
                    runtimeId = it.runtimeId,
                    runtimeAgentId = it.runtimeAgentId,
                    organizationId = it.organizationId,
                    identityStatus = it.identityStatus
                )
            }
        val runtimeInfrastructureAccesses = runtimeInfrastructureAccessView.findAll(Pageable.unpaged()).content
            .map {
                RuntimeInfrastructureAccessSnapshot(
                    runtimeAgentId = it.runtimeAgentId,
                    state = it.state?.name
                )
            }
        val datasetMetadata = runtimeDatasetMetadataCatalog.findAll(Pageable.unpaged()).content
            .map {
                RuntimeDatasetMetadataSnapshot(
                    datasetId = it.datasetId,
                    organizationId = it.organizationId,
                    runtimeId = it.runtimeId,
                    featureSchemaId = it.featureSchemaId,
                    schemaCompatible = it.schemaCompatible,
                    labelCompatible = it.labelCompatible
                )
            }

        log.info(
            "Loaded participant selection snapshot. trainingJobId={}, federationId={}, featureSchemaId={}, membershipCount={}, runtimeIdentityCount={}, runtimeInfrastructureAccessCount={}, datasetMetadataCount={}",
            input.trainingJobId,
            federationId,
            featureSchemaId,
            memberships.size,
            runtimeIdentities.size,
            runtimeInfrastructureAccesses.size,
            datasetMetadata.size
        )

        return SelectTrainingRoundParticipantsResult.Succeeded(
            trainingRunConfigurationId = trainingRunConfigurationId,
            federationId = federationId,
            featureSchemaId = featureSchemaId,
            currentRoundNumber = trainingJob?.currentRoundNumber,
            minimumNodesPerRound = minimumNodesPerRound,
            memberships = memberships,
            runtimeIdentities = runtimeIdentities,
            runtimeInfrastructureAccesses = runtimeInfrastructureAccesses,
            datasetMetadata = datasetMetadata
        )
    }
}
