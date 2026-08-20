package tech.medo.infrastructure.secondary.trainingorchestration.participantexecutionplan.generateparticipantexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
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

class ReadModelParticipantExecutionPlanCommandFactoryTest {
    private val trainingRunConfigurationId = uuid("11111111-1111-4111-8111-111111111111")
    private val featureSchemaId = uuid("22222222-2222-4222-8222-222222222222")
    private val trainingJobId = uuid("33333333-3333-4333-8333-333333333333")
    private val roundId = uuid("44444444-4444-4444-8444-444444444444")
    private val initialModelId = uuid("55555555-5555-4555-8555-555555555555")

    @Test
    fun fansOutOneExecutionPlanCommandPerSelectedParticipant() {
        val factory = ReadModelParticipantExecutionPlanCommandFactory(
            trainingRunConfigurationCatalog = configurationRepository(configuration()),
            trainingRoundProgress = progressRepository()
        )

        val commands = factory.buildCommands(
            event(
                participants = listOf(
                    TrainingRoundParticipant(
                        organizationId = uuid("66666666-6666-4666-8666-666666666666"),
                        runtimeId = uuid("77777777-7777-4777-8777-777777777777"),
                        datasetId = uuid("88888888-8888-4888-8888-888888888888")
                    ),
                    TrainingRoundParticipant(
                        organizationId = uuid("99999999-9999-4999-8999-999999999999"),
                        runtimeId = uuid("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
                        datasetId = uuid("bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb")
                    )
                )
            )
        )

        assertEquals(2, commands.size)
        assertEquals(
            listOf(
                uuid("77777777-7777-4777-8777-777777777777"),
                uuid("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa")
            ),
            commands.map { it.runtimeId }
        )
        commands.forEach { command ->
            assertEquals(trainingJobId, command.trainingJobId)
            assertEquals(trainingRunConfigurationId, command.trainingRunConfigurationId)
            assertEquals(featureSchemaId, command.featureSchemaId)
            assertEquals(roundId, command.roundId)
            assertEquals(1, command.roundNumber)
            assertEquals(initialModelId, command.baseModelId)
            assertEquals("file:///models/initial.json", command.baseModelArtifactUri)
            assertEquals("local", command.baseModelRegistryRef)
            assertEquals("json", command.baseModelFormat)
            assertEquals("sha256:initial", command.baseModelArtifactDigest)
            assertEquals("file:///models/initial.sig", command.baseModelSignatureUri)
        }
    }

    @Test
    fun fallsBackToInitialModelWhenPreviousRoundDoesNotExposeArtifactSnapshot() {
        val previousRound = TrainingRoundProgressReadModelProjection().apply {
            trainingJobId = this@ReadModelParticipantExecutionPlanCommandFactoryTest.trainingJobId
            roundId = uuid("cccccccc-cccc-4ccc-8ccc-cccccccccccc")
            roundNumber = 1
            aggregatedModelId = uuid("dddddddd-dddd-4ddd-8ddd-dddddddddddd")
        }
        val factory = ReadModelParticipantExecutionPlanCommandFactory(
            trainingRunConfigurationCatalog = configurationRepository(configuration()),
            trainingRoundProgress = progressRepository(previousRound)
        )

        val command = factory.buildCommands(
            event(
                roundNumber = 2,
                participants = listOf(
                    TrainingRoundParticipant(
                        organizationId = uuid("66666666-6666-4666-8666-666666666666"),
                        runtimeId = uuid("77777777-7777-4777-8777-777777777777"),
                        datasetId = uuid("88888888-8888-4888-8888-888888888888")
                    )
                )
            )
        ).single()

        assertEquals(initialModelId, command.baseModelId)
        assertEquals("file:///models/initial.json", command.baseModelArtifactUri)
        assertEquals("sha256:initial", command.baseModelArtifactDigest)
    }

    private fun event(
        roundNumber: Int = 1,
        participants: List<TrainingRoundParticipant>
    ): TrainingRoundStartedEvent =
        TrainingRoundStartedEvent(
            trainingJobId = trainingJobId,
            trainingRunConfigurationId = trainingRunConfigurationId,
            featureSchemaId = featureSchemaId,
            roundId = roundId,
            roundNumber = roundNumber,
            selectedOrganizationIds = participants.map { it.organizationId },
            selectedRuntimeIds = participants.map { it.runtimeId },
            selectedParticipants = participants,
            selectedOrganizationCount = participants.map { it.organizationId }.distinct().size,
            selectedRuntimeCount = participants.map { it.runtimeId }.distinct().size,
            minimumNodesPerRound = 1
        )

    private fun configuration(): TrainingRunConfigurationCatalogReadModelProjection =
        TrainingRunConfigurationCatalogReadModelProjection().apply {
            trainingRunConfigurationId = this@ReadModelParticipantExecutionPlanCommandFactoryTest.trainingRunConfigurationId
            featureSchemaId = this@ReadModelParticipantExecutionPlanCommandFactoryTest.featureSchemaId
            initialModelId = this@ReadModelParticipantExecutionPlanCommandFactoryTest.initialModelId
            initialModelArtifactUri = "file:///models/initial.json"
            initialModelRegistryRef = "local"
            initialModelFormat = "json"
            initialModelArtifactDigest = "sha256:initial"
            initialModelSignatureUri = "file:///models/initial.sig"
        }

    private fun configurationRepository(
        projection: TrainingRunConfigurationCatalogReadModelProjection
    ): TrainingRunConfigurationCatalogReadModelRepository =
        object : TrainingRunConfigurationCatalogReadModelRepository {
            override fun findAll(pageable: Pageable): Page<TrainingRunConfigurationCatalogReadModel> =
                PageImpl(emptyList())

            override fun findAllByCriteria(
                criteria: TrainingRunConfigurationCatalogReadModelCriteria?,
                pageable: Pageable
            ): Page<TrainingRunConfigurationCatalogReadModel> =
                findAll(pageable)

            override fun findById(id: UUID): TrainingRunConfigurationCatalogReadModel? = null

            override fun findProjectionById(id: UUID): TrainingRunConfigurationCatalogReadModelProjection? =
                projection.takeIf { id == projection.trainingRunConfigurationId }

            override fun save(projection: TrainingRunConfigurationCatalogReadModelProjection) = Unit
        }

    private fun progressRepository(
        vararg projections: TrainingRoundProgressReadModelProjection
    ): TrainingRoundProgressReadModelRepository =
        object : TrainingRoundProgressReadModelRepository {
            override fun findAll(pageable: Pageable): Page<TrainingRoundProgressReadModel> =
                PageImpl(emptyList())

            override fun findAllByCriteria(
                criteria: TrainingRoundProgressReadModelCriteria?,
                pageable: Pageable
            ): Page<TrainingRoundProgressReadModel> =
                findAll(pageable)

            override fun findById(id: TrainingRoundProgressReadModelKey): TrainingRoundProgressReadModel? = null
            override fun findProjectionById(id: TrainingRoundProgressReadModelKey): TrainingRoundProgressReadModelProjection? = null

            override fun findProjectionsByTrainingJobId(trainingJobId: UUID): List<TrainingRoundProgressReadModelProjection> =
                projections.filter { it.trainingJobId == trainingJobId }

            override fun findProjectionsByRoundId(roundId: UUID): List<TrainingRoundProgressReadModelProjection> =
                projections.filter { it.roundId == roundId }

            override fun save(projection: TrainingRoundProgressReadModelProjection) = Unit
        }

    private fun uuid(value: String): UUID = UUID.fromString(value)
}
