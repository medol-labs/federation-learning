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
import java.math.BigDecimal
import java.time.LocalDateTime
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
        val trainingRunConfigurationId = input.trainingRunConfigurationId
        val federationId = input.federationId
        val featureSchemaId = input.featureSchemaId
        val maxRounds = trainingRunConfiguration?.maxRounds ?: trainingJob?.maxRounds
        val minimumAccuracy = trainingRunConfiguration?.minimumAccuracy
        val aggregationAlgorithm = trainingRunConfiguration?.aggregationAlgorithm
        val minimumNodesPerRound = trainingRunConfiguration?.minimumNodesPerRound ?: trainingJob?.minimumNodesPerRound
        val secureAggregationRequired = trainingRunConfiguration?.secureAggregationRequired ?: trainingJob?.secureAggregationRequired ?: false

        if (
            maxRounds == null ||
            maxRounds <= 0 ||
            minimumAccuracy == null ||
            aggregationAlgorithm == null ||
            minimumNodesPerRound == null ||
            minimumNodesPerRound <= 0
        ) {
            log.warn(
                "Skip training round participant selection because job context is incomplete. trainingJobId={}, trainingRunConfigurationIdPresent={}, federationIdPresent={}, featureSchemaIdPresent={}, maxRounds={}, minimumAccuracy={}, minimumNodesPerRound={}",
                input.trainingJobId,
                true,
                true,
                true,
                maxRounds,
                minimumAccuracy,
                minimumNodesPerRound
            )
            error("Training job context is incomplete for participant selection.")
        }

        val roundId = UUID.randomUUID()
        val roundNumber = (trainingJob.currentRoundNumber ?: 0) + 1

        val contextMismatches = buildList {
            if (trainingJob.federationId != null && trainingJob.federationId != federationId) {
                add("training job federationId=${trainingJob.federationId}")
            }
            if (trainingRunConfiguration?.federationId != null && trainingRunConfiguration.federationId != federationId) {
                add("training configuration federationId=${trainingRunConfiguration.federationId}")
            }
            if (trainingJob.featureSchemaId != null && trainingJob.featureSchemaId != featureSchemaId) {
                add("training job featureSchemaId=${trainingJob.featureSchemaId}")
            }
            if (trainingRunConfiguration?.featureSchemaId != null && trainingRunConfiguration.featureSchemaId != featureSchemaId) {
                add("training configuration featureSchemaId=${trainingRunConfiguration.featureSchemaId}")
            }
        }
        if (contextMismatches.isNotEmpty()) {
            val failureReason =
                "Training selection context does not match the submitted federation and feature schema: " +
                    contextMismatches.joinToString()
            log.warn(
                "Training round participant selection failed context validation. trainingJobId={}, federationId={}, featureSchemaId={}, mismatches={}",
                input.trainingJobId,
                federationId,
                featureSchemaId,
                contextMismatches
            )
            return rejected(
                roundId = roundId,
                roundNumber = roundNumber,
                maxRounds = maxRounds,
                minimumAccuracy = minimumAccuracy,
                aggregationAlgorithm = aggregationAlgorithm,
                minimumNodesPerRound = minimumNodesPerRound,
                secureAggregationRequired = secureAggregationRequired,
                failureReason = failureReason
            )
        }

        val federationMemberships = membershipDirectory.findProjectionsByFederationId(federationId)
        val joinedOrganizationIds = federationMemberships
            .filter { it.organizationId != null }
            .filter { it.membershipStatus.isJoinedLike() }
            .mapNotNull { it.organizationId }
            .toSet()

        if (joinedOrganizationIds.isEmpty()) {
            val failureReason =
                "Federation $federationId has no joined organizations available for participant selection."
            log.warn(
                "Training round participant selection failed federation validation. trainingJobId={}, federationId={}, membershipCount={}, membershipStatuses={}, failureReason={}",
                input.trainingJobId,
                federationId,
                federationMemberships.size,
                federationMemberships.map { it.membershipStatus },
                failureReason
            )
            return rejected(
                roundId = roundId,
                roundNumber = roundNumber,
                maxRounds = maxRounds,
                minimumAccuracy = minimumAccuracy,
                aggregationAlgorithm = aggregationAlgorithm,
                minimumNodesPerRound = minimumNodesPerRound,
                secureAggregationRequired = secureAggregationRequired,
                failureReason = failureReason
            )
        }

        val matchingDatasetMetadata = findMatchingDatasetMetadata(featureSchemaId, joinedOrganizationIds)
        log.info(
            "Compatible runtime dataset metadata for participant selection. trainingJobId={}, federationId={}, featureSchemaId={}, joinedOrganizationIds={}, metadata={}",
            input.trainingJobId,
            federationId,
            featureSchemaId,
            joinedOrganizationIds,
            matchingDatasetMetadata.map {
                mapOf(
                    "runtimeDatasetBindingId" to it.runtimeDatasetBindingId,
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

        if (matchingDatasetMetadata.isEmpty()) {
            val failureReason =
                "No schema-compatible and label-compatible dataset metadata was found for feature schema " +
                    "$featureSchemaId in joined federation organizations."
            log.warn(
                "Training round participant selection failed dataset validation. trainingJobId={}, federationId={}, featureSchemaId={}, joinedOrganizationIds={}, failureReason={}",
                input.trainingJobId,
                federationId,
                featureSchemaId,
                joinedOrganizationIds,
                failureReason
            )
            return rejected(
                roundId = roundId,
                roundNumber = roundNumber,
                maxRounds = maxRounds,
                minimumAccuracy = minimumAccuracy,
                aggregationAlgorithm = aggregationAlgorithm,
                minimumNodesPerRound = minimumNodesPerRound,
                secureAggregationRequired = secureAggregationRequired,
                failureReason = failureReason
            )
        }

        val organizationsWithCompatibleDatasets = matchingDatasetMetadata.mapNotNull { it.organizationId }.toSet()
        val activeRuntimes = findActiveRuntimeIdentities(organizationsWithCompatibleDatasets)
            .associateBy { it.runtimeId!! }

        if (activeRuntimes.isEmpty()) {
            val failureReason =
                "No active runtime identity was found for organizations with compatible datasets."
            log.warn(
                "Training round participant selection failed runtime validation. trainingJobId={}, federationId={}, featureSchemaId={}, organizationIds={}, failureReason={}",
                input.trainingJobId,
                federationId,
                featureSchemaId,
                organizationsWithCompatibleDatasets,
                failureReason
            )
            return rejected(
                roundId = roundId,
                roundNumber = roundNumber,
                maxRounds = maxRounds,
                minimumAccuracy = minimumAccuracy,
                aggregationAlgorithm = aggregationAlgorithm,
                minimumNodesPerRound = minimumNodesPerRound,
                secureAggregationRequired = secureAggregationRequired,
                failureReason = failureReason
            )
        }

        val selectedParticipants = matchingDatasetMetadata
            .mapNotNull { metadata ->
                val runtime = activeRuntimes[metadata.runtimeId]
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
            val unmatchedDatasetRuntimeIds = matchingDatasetMetadata.mapNotNull { it.runtimeId }
                .filterNot(activeRuntimes::containsKey)
                .distinct()
            val failureReason =
                "Selected runtime count ${selectedRuntimeIds.size} is below minimum nodes per round " +
                    "$minimumNodesPerRound after exact runtime-to-dataset matching. " +
                    "Unmatched dataset runtime IDs: $unmatchedDatasetRuntimeIds."
            log.warn(
                "Training round participant selection failed below quorum. trainingJobId={}, federationId={}, featureSchemaId={}, selectedRuntimeCount={}, minimumNodesPerRound={}, failureReason={}",
                input.trainingJobId,
                federationId,
                featureSchemaId,
                selectedRuntimeIds.size,
                minimumNodesPerRound,
                failureReason
            )
            return rejected(
                roundId = roundId,
                roundNumber = roundNumber,
                maxRounds = maxRounds,
                minimumAccuracy = minimumAccuracy,
                aggregationAlgorithm = aggregationAlgorithm,
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
            roundId = roundId,
            roundNumber = roundNumber,
            maxRounds = maxRounds,
            minimumAccuracy = minimumAccuracy,
            aggregationAlgorithm = aggregationAlgorithm,
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
                    cb.equal(root.get<UUID>("featureSchemaId"), featureSchemaId),
                    root.get<UUID>("organizationId").`in`(organizationIds),
                    cb.isNotNull(root.get<UUID>("runtimeId")),
                    cb.isNotNull(root.get<UUID>("datasetId")),
                    cb.isTrue(root.get("schemaCompatible")),
                    cb.isTrue(root.get("labelCompatible"))
                )
            )
            query.orderBy(
                cb.desc(root.get<LocalDateTime>("profiledAt")),
                cb.asc(root.get<UUID>("runtimeDatasetBindingId"))
            )
            entityManager.createQuery(query).resultList
        }

    private fun rejected(
        roundId: UUID,
        roundNumber: Int,
        maxRounds: Int,
        minimumAccuracy: BigDecimal,
        aggregationAlgorithm: String,
        minimumNodesPerRound: Int,
        secureAggregationRequired: Boolean,
        selectedOrganizationIds: List<UUID> = emptyList(),
        selectedRuntimeIds: List<UUID> = emptyList(),
        selectedParticipants: List<TrainingRoundParticipant> = emptyList(),
        selectedOrganizationCount: Int = selectedOrganizationIds.size,
        selectedRuntimeCount: Int = selectedRuntimeIds.size,
        failureReason: String
    ): SelectTrainingRoundParticipantsResult.Rejected =
        SelectTrainingRoundParticipantsResult.Rejected(
            roundId = roundId,
            roundNumber = roundNumber,
            maxRounds = maxRounds,
            minimumAccuracy = minimumAccuracy,
            aggregationAlgorithm = aggregationAlgorithm,
            minimumNodesPerRound = minimumNodesPerRound,
            secureAggregationRequired = secureAggregationRequired,
            selectedOrganizationIds = selectedOrganizationIds,
            selectedRuntimeIds = selectedRuntimeIds,
            selectedParticipants = selectedParticipants,
            selectedOrganizationCount = selectedOrganizationCount,
            selectedRuntimeCount = selectedRuntimeCount,
            failureReason = failureReason
        )

}
