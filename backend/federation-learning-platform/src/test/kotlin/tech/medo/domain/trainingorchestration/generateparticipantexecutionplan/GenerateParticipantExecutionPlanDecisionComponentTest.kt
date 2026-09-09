package tech.medo.domain.trainingorchestration.generateparticipantexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModel
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelCriteria
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelKey
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelProjection
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModel
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelCriteria
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelProjection
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import java.util.UUID

class GenerateParticipantExecutionPlanDecisionComponentTest {
    private val trainingRunConfigurationId = uuid("11111111-1111-4111-8111-111111111111")
    private val featureSchemaId = uuid("22222222-2222-4222-8222-222222222222")
    private val trainingJobId = uuid("33333333-3333-4333-8333-333333333333")
    private val roundId = uuid("44444444-4444-4444-8444-444444444444")
    private val initialModelId = uuid("55555555-5555-4555-8555-555555555555")

    @Test
    fun fillsBaseModelSnapshotFromTrainingRunConfiguration() {
        val decision = decision(configuration())

        val event = decision.decide(command()).single() as ParticipantExecutionPlanGeneratedEvent

        assertEquals(trainingJobId, event.trainingJobId)
        assertEquals(trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(featureSchemaId, event.featureSchemaId)
        assertEquals(roundId, event.roundId)
        assertEquals(1, event.roundNumber)
        assertEquals(uuid("77777777-7777-4777-8777-777777777777"), event.runtimeId)
        assertEquals(initialModelId, event.baseModelId)
        assertEquals("file:///models/initial.json", event.baseModelArtifactUri)
        assertEquals("local", event.baseModelRegistryRef)
        assertEquals("json", event.baseModelFormat)
        assertEquals("sha256:initial", event.baseModelArtifactDigest)
        assertEquals("file:///models/initial.sig", event.baseModelSignatureUri)
    }

    @Test
    fun fallsBackToInitialModelWhenPreviousRoundDoesNotExposeArtifactSnapshot() {
        val previousRound = TrainingRoundProgressReadModelProjection().apply {
            trainingJobId = this@GenerateParticipantExecutionPlanDecisionComponentTest.trainingJobId
            roundId = uuid("cccccccc-cccc-4ccc-8ccc-cccccccccccc")
            roundNumber = 1
            aggregatedModelId = uuid("dddddddd-dddd-4ddd-8ddd-dddddddddddd")
        }
        val decision = decision(configuration(), previousRound)

        val event = decision.decide(command(roundNumber = 2)).single() as ParticipantExecutionPlanGeneratedEvent

        assertEquals(initialModelId, event.baseModelId)
        assertEquals("file:///models/initial.json", event.baseModelArtifactUri)
        assertEquals("sha256:initial", event.baseModelArtifactDigest)
    }

    private fun decision(
        configuration: TrainingRunConfigurationCatalogReadModelProjection,
        vararg progress: TrainingRoundProgressReadModelProjection,
    ): GenerateParticipantExecutionPlanDecisionComponent =
        GenerateParticipantExecutionPlanDecisionComponent(
            trainingRunConfigurationCatalog = configurationRepository(configuration),
            trainingRoundProgress = progressRepository(*progress),
        )

    private fun command(roundNumber: Int = 1): GenerateParticipantExecutionPlanCommand =
        GenerateParticipantExecutionPlanCommand(
            trainingJobId = trainingJobId,
            trainingRunConfigurationId = trainingRunConfigurationId,
            featureSchemaId = featureSchemaId,
            roundId = roundId,
            roundNumber = roundNumber,
            runtimeId = uuid("77777777-7777-4777-8777-777777777777"),
            organizationId = uuid("66666666-6666-4666-8666-666666666666"),
            baseModelId = uuid("99999999-9999-4999-8999-999999999999"),
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null,
            secureAggregationRequired = true,
            secureAggregationSessionId = uuid("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
            encryptionScheme = "PAILLIER",
            publicKeyVersion = "local-dev-v1",
            publicKeyRef = "local://secure-aggregation/public-keys/local-dev-v1",
            encryptedParameterScale = 1000000,
        )

    private fun configuration(): TrainingRunConfigurationCatalogReadModelProjection =
        TrainingRunConfigurationCatalogReadModelProjection().apply {
            trainingRunConfigurationId = this@GenerateParticipantExecutionPlanDecisionComponentTest.trainingRunConfigurationId
            featureSchemaId = this@GenerateParticipantExecutionPlanDecisionComponentTest.featureSchemaId
            initialModelId = this@GenerateParticipantExecutionPlanDecisionComponentTest.initialModelId
            initialModelArtifactUri = "file:///models/initial.json"
            initialModelRegistryRef = "local"
            initialModelFormat = "json"
            initialModelArtifactDigest = "sha256:initial"
            initialModelSignatureUri = "file:///models/initial.sig"
        }

    private fun configurationRepository(
        projection: TrainingRunConfigurationCatalogReadModelProjection,
    ): TrainingRunConfigurationCatalogReadModelRepository =
        object : TrainingRunConfigurationCatalogReadModelRepository {
            override fun findAll(pageable: Pageable): Page<TrainingRunConfigurationCatalogReadModel> =
                PageImpl(emptyList())

            override fun findAllByCriteria(
                criteria: TrainingRunConfigurationCatalogReadModelCriteria?,
                pageable: Pageable,
            ): Page<TrainingRunConfigurationCatalogReadModel> =
                findAll(pageable)

            override fun findById(id: UUID): TrainingRunConfigurationCatalogReadModel? = null

            override fun findProjectionById(id: UUID): TrainingRunConfigurationCatalogReadModelProjection? =
                projection.takeIf { id == projection.trainingRunConfigurationId }

            override fun save(projection: TrainingRunConfigurationCatalogReadModelProjection) = Unit
        }

    private fun progressRepository(
        vararg projections: TrainingRoundProgressReadModelProjection,
    ): TrainingRoundProgressReadModelRepository =
        object : TrainingRoundProgressReadModelRepository {
            override fun findAll(pageable: Pageable): Page<TrainingRoundProgressReadModel> =
                PageImpl(emptyList())

            override fun findAllByCriteria(
                criteria: TrainingRoundProgressReadModelCriteria?,
                pageable: Pageable,
            ): Page<TrainingRoundProgressReadModel> =
                findAll(pageable)

            override fun findById(id: TrainingRoundProgressReadModelKey): TrainingRoundProgressReadModel? = null

            override fun findProjectionById(id: TrainingRoundProgressReadModelKey): TrainingRoundProgressReadModelProjection? =
                null

            override fun findProjectionsByTrainingJobId(trainingJobId: UUID): List<TrainingRoundProgressReadModelProjection> =
                projections.filter { it.trainingJobId == trainingJobId }

            override fun findProjectionsByRoundId(roundId: UUID): List<TrainingRoundProgressReadModelProjection> =
                projections.filter { it.roundId == roundId }

            override fun save(projection: TrainingRoundProgressReadModelProjection) = Unit
        }

    private fun uuid(value: String): UUID = UUID.fromString(value)
}
