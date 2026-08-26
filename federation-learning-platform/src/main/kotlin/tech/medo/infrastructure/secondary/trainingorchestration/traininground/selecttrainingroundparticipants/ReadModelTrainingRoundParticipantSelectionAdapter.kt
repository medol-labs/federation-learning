package tech.medo.infrastructure.secondary.trainingorchestration.traininground.selecttrainingroundparticipants

import jakarta.persistence.EntityManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.infrastructure.secondary.persistence.runtimedatasetmetadatacatalogreadmodel.RuntimeDatasetMetadataCatalogReadModelEntity
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelRepository
import tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimeidentitycatalogreadmodel.RuntimeIdentityCatalogReadModelEntity
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
    private val entityManager: EntityManager
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
        val secureAggregationRequired = trainingRunConfiguration?.secureAggregationRequired ?: trainingJob?.secureAggregationRequired ?: false

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

        if (joinedOrganizationIds.isEmpty()) {
            log.info(
                "No joined federation members found for participant selection. trainingJobId={}, federationId={}",
                input.trainingJobId,
                federationId
            )
        }

        val activeRuntimes = if (joinedOrganizationIds.isEmpty()) {
            emptyMap()
        } else {
            findActiveRuntimeIdentities(joinedOrganizationIds)
            .associateBy { it.runtimeId!! }
        }

        val activeRuntimeByOrganization = activeRuntimes.values
            .groupBy { it.organizationId!! }
            .mapValues { (_, runtimes) -> runtimes.first() }

        val matchingDatasetMetadata = if (joinedOrganizationIds.isEmpty()) {
            emptyList()
        } else {
            findMatchingDatasetMetadata(featureSchemaId, joinedOrganizationIds)
        }
        log.info(
            "Matching runtime dataset metadata for participant selection. trainingJobId={}, featureSchemaId={}, metadata={}",
            input.trainingJobId,
            featureSchemaId,
            matchingDatasetMetadata.map {
                mapOf(
                    "metadataReportId" to it.metadataReportId,
                    "organizationId" to it.organizationId,
                    "runtimeId" to it.runtimeId,
                    "datasetId" to it.datasetId,
                    "datasetName" to it.datasetName,
                    "schemaCompatible" to it.schemaCompatible,
                    "labelCompatible" to it.labelCompatible
                )
            }
        )
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
        log.info("Selected training round participants. trainingJobId={}, selectedParticipants={}", input.trainingJobId, selectedParticipants)
        val selectedOrganizationIds = selectedParticipants.map { it.organizationId }.distinct()
        val selectedRuntimeIds = selectedParticipants.map { it.runtimeId }.distinct()
        val roundId = UUID.randomUUID()
        val roundNumber = (trainingJob?.currentRoundNumber ?: 0) + 1

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

        if (selectedRuntimeIds.size < minimumNodesPerRound) {
            val failureReason = "Selected runtime count ${selectedRuntimeIds.size} is below minimum nodes per round $minimumNodesPerRound."
            log.warn(
                "Training round participant selection failed below quorum. trainingJobId={}, federationId={}, featureSchemaId={}, selectedRuntimeCount={}, minimumNodesPerRound={}, failureReason={}",
                input.trainingJobId,
                federationId,
                featureSchemaId,
                selectedRuntimeIds.size,
                minimumNodesPerRound,
                failureReason
            )
            return SelectTrainingRoundParticipantsResult.Rejected(
                trainingRunConfigurationId = trainingRunConfigurationId,
                featureSchemaId = featureSchemaId,
                roundId = roundId,
                roundNumber = roundNumber,
                minimumNodesPerRound = minimumNodesPerRound,
                secureAggregationRequired = secureAggregationRequired,
                selectedOrganizationIds = selectedOrganizationIds,
                selectedRuntimeIds = selectedRuntimeIds,
                selectedParticipants = selectedParticipants,
                selectedOrganizationCount = selectedOrganizationIds.size,
                selectedRuntimeCount = selectedRuntimeIds.size,
                failureReason = failureReason
            )
        }

        return SelectTrainingRoundParticipantsResult.Succeeded(
            trainingRunConfigurationId = trainingRunConfigurationId,
            featureSchemaId = featureSchemaId,
            roundId = roundId,
            roundNumber = roundNumber,
            minimumNodesPerRound = minimumNodesPerRound,
            secureAggregationRequired = secureAggregationRequired,
            selectedOrganizationIds = selectedOrganizationIds,
            selectedRuntimeIds = selectedRuntimeIds,
            selectedParticipants = selectedParticipants,
            selectedOrganizationCount = selectedOrganizationIds.size,
            selectedRuntimeCount = selectedRuntimeIds.size
        )
    }

    private fun String?.isJoinedLike(): Boolean =
        this == null || equals("Joined", ignoreCase = true) || equals("Approved", ignoreCase = true) || equals("Active", ignoreCase = true)

    private fun findActiveRuntimeIdentities(organizationIds: Set<UUID>): List<RuntimeIdentityCatalogReadModelEntity> =
        entityManager.criteriaBuilder.let { cb ->
            val query = cb.createQuery(RuntimeIdentityCatalogReadModelEntity::class.java)
            val root = query.from(RuntimeIdentityCatalogReadModelEntity::class.java)
            query.select(root).where(
                cb.and(
                    cb.isNotNull(root.get<UUID>("runtimeId")),
                    cb.isNotNull(root.get<UUID>("organizationId")),
                    root.get<UUID>("organizationId").`in`(organizationIds),
                    cb.or(
                        cb.isNull(root.get<String>("identityStatus")),
                        cb.equal(cb.lower(root.get("identityStatus")), "active")
                    )
                )
            )
            entityManager.createQuery(query).resultList
        }

    private fun findMatchingDatasetMetadata(
        featureSchemaId: UUID,
        organizationIds: Set<UUID>
    ): List<RuntimeDatasetMetadataCatalogReadModelEntity> =
        entityManager.criteriaBuilder.let { cb ->
            val query = cb.createQuery(RuntimeDatasetMetadataCatalogReadModelEntity::class.java)
            val root = query.from(RuntimeDatasetMetadataCatalogReadModelEntity::class.java)
            query.select(root).where(
                cb.and(
                    // cb.equal(root.get<UUID>("featureSchemaId"), featureSchemaId), // TODO 解决 feature schema id 来源问题
                    root.get<UUID>("organizationId").`in`(organizationIds),
                    cb.isNotNull(root.get<UUID>("datasetId"))
                )
            )
            entityManager.createQuery(query).resultList
        }

}
