package tech.medo.domain.trainingorchestration.generateparticipantexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModel
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelCriteria
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelProjection
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModel
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelCriteria
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelProjection
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelRepository
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
        assertEquals("SKLEARN_LOGISTIC_REGRESSION", event.baseModelPlugin)
        assertEquals("json", event.baseModelFormat)
        assertEquals("sha256:initial", event.baseModelArtifactDigest)
        assertEquals("file:///models/initial.sig", event.baseModelSignatureUri)
    }

    @Test
    fun fillsBaseModelSnapshotFromPreviousAggregatedModelForLaterRounds() {
        val aggregatedModelId = uuid("dddddddd-dddd-4ddd-8ddd-dddddddddddd")
        val previousRound = TrainingRoundProgressReadModelProjection().apply {
            trainingJobId = this@GenerateParticipantExecutionPlanDecisionComponentTest.trainingJobId
            roundId = uuid("cccccccc-cccc-4ccc-8ccc-cccccccccccc")
            roundNumber = 1
            this.aggregatedModelId = aggregatedModelId
        }
        val decision = decision(
            configuration = configuration(),
            progress = arrayOf(previousRound),
            modelArtifacts = arrayOf(aggregatedModel(aggregatedModelId)),
        )

        val event = decision.decide(command(roundNumber = 2)).single() as ParticipantExecutionPlanGeneratedEvent

        assertEquals(aggregatedModelId, event.baseModelId)
        assertEquals("file:///models/aggregated.json", event.baseModelArtifactUri)
        assertEquals("sha256:aggregated", event.baseModelArtifactDigest)
    }

    @Test
    fun fillsPersonalizedBaseModelSnapshotForPersonalizedLaterRounds() {
        val personalizedModelId = uuid("12121212-1212-4121-8121-121212121212")
        val targetId = uuid("abababab-abab-4aba-8bab-abababababab")
        val decision = decision(
            configuration = configuration(),
            personalizedModels = arrayOf(personalizedModel(personalizedModelId, targetId)),
        )

        val event = decision.decide(
            command(
                roundNumber = 2,
                personalizationMode = "LOCAL_FINE_TUNE",
                personalizedArtifactPolicy = "REGISTER_PER_TARGET",
                personalizationTargetType = "PARTICIPANT",
                personalizationTargetId = targetId,
            ),
        ).single() as ParticipantExecutionPlanGeneratedEvent

        assertEquals(personalizedModelId, event.personalizedBaseModelId)
        assertEquals("file:///models/personalized.json", event.personalizedBaseModelArtifactUri)
        assertEquals("sha256:personalized", event.personalizedBaseModelArtifactDigest)
    }

    private fun decision(
        configuration: TrainingRunConfigurationCatalogReadModelProjection,
        progress: Array<TrainingRoundProgressReadModelProjection> = emptyArray(),
        modelArtifacts: Array<ModelArtifactCatalogReadModelProjection> = emptyArray(),
        personalizedModels: Array<PersonalizedModelCatalogReadModelProjection> = emptyArray(),
    ): GenerateParticipantExecutionPlanDecisionComponent =
        GenerateParticipantExecutionPlanDecisionComponent(
            trainingRunConfigurationCatalog = configurationRepository(configuration),
            trainingRoundProgress = progressRepository(*progress),
            modelArtifactCatalog = modelArtifactRepository(*modelArtifacts),
            personalizedModelCatalog = personalizedModelRepository(*personalizedModels),
        )

    private fun command(
        roundNumber: Int = 1,
        personalizationMode: String? = "GLOBAL_ONLY",
        personalizedArtifactPolicy: String? = "NONE",
        personalizationTargetType: String? = null,
        personalizationTargetId: UUID? = null,
    ): GenerateParticipantExecutionPlanCommand =
        GenerateParticipantExecutionPlanCommand(
            trainingJobId = trainingJobId,
            federationId = uuid("88888888-8888-4888-8888-888888888888"),
            federationName = "Test Federation",
            trainingRunConfigurationId = trainingRunConfigurationId,
            configurationName = "Default Training Configuration",
            trainingJobObjective = "Train fraud detection model",
            featureSchemaId = featureSchemaId,
            roundId = roundId,
            roundNumber = roundNumber,
            runtimeId = uuid("77777777-7777-4777-8777-777777777777"),
            organizationId = uuid("66666666-6666-4666-8666-666666666666"),
            personalizationMode = personalizationMode,
            personalizationScope = "FEDERATION",
            personalizedArtifactPolicy = personalizedArtifactPolicy,
            personalizationMetric = null,
            minimumPersonalizationLift = null,
            personalizationTargetType = personalizationTargetType,
            personalizationTargetId = personalizationTargetId,
            personalizationCohortId = null,
            personalizationProfileId = null,
            baseModelId = uuid("99999999-9999-4999-8999-999999999999"),
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelPlugin = "SKLEARN_LOGISTIC_REGRESSION",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null,
            personalizedBaseModelId = null,
            personalizedBaseModelArtifactUri = null,
            personalizedBaseModelArtifactDigest = null,
            runtimeEngineProfileId = uuid("bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb"),
            runtimeEngineProfileName = "Python Linear Runtime",
            runtimeEnginePluginProfile = "python-linear",
            runtimeEngineImage = "registry.example.com/runtime-engine:1.0.0",
            runtimeEngineImageDigest = "sha256:runtime-engine",
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
            initialModelPlugin = "SKLEARN_LOGISTIC_REGRESSION"
            initialModelFormat = "json"
            initialModelArtifactDigest = "sha256:initial"
            initialModelSignatureUri = "file:///models/initial.sig"
            runtimeEngineProfileId = uuid("bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb")
            runtimeEngineProfileName = "Python Linear Runtime"
            runtimeEnginePluginProfile = "python-linear"
            runtimeEngineImage = "registry.example.com/runtime-engine:1.0.0"
            runtimeEngineImageDigest = "sha256:runtime-engine"
        }

    private fun aggregatedModel(modelId: UUID): ModelArtifactCatalogReadModelProjection =
        ModelArtifactCatalogReadModelProjection().apply {
            this.modelId = modelId
            modelArtifactUri = "file:///models/aggregated.json"
            modelRegistryRef = "local"
            modelPlugin = "SKLEARN_LOGISTIC_REGRESSION"
            modelFormat = "json"
            modelArtifactDigest = "sha256:aggregated"
            modelSignatureUri = "file:///models/aggregated.sig"
        }

    private fun personalizedModel(modelId: UUID, targetId: UUID): PersonalizedModelCatalogReadModelProjection =
        PersonalizedModelCatalogReadModelProjection().apply {
            personalizedModelId = modelId
            trainingJobId = this@GenerateParticipantExecutionPlanDecisionComponentTest.trainingJobId
            trainingRunConfigurationId = this@GenerateParticipantExecutionPlanDecisionComponentTest.trainingRunConfigurationId
            runtimeId = uuid("77777777-7777-4777-8777-777777777777")
            featureSchemaId = this@GenerateParticipantExecutionPlanDecisionComponentTest.featureSchemaId
            roundNumber = 1
            personalizationTargetType = "PARTICIPANT"
            personalizationTargetId = targetId
            personalizedModelArtifactRef = "file:///models/personalized.json"
            personalizedModelArtifactDigest = "sha256:personalized"
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

    private fun modelArtifactRepository(
        vararg projections: ModelArtifactCatalogReadModelProjection,
    ): ModelArtifactCatalogReadModelRepository =
        object : ModelArtifactCatalogReadModelRepository {
            override fun findAll(pageable: Pageable): Page<ModelArtifactCatalogReadModel> =
                PageImpl(emptyList())

            override fun findAllByCriteria(
                criteria: ModelArtifactCatalogReadModelCriteria?,
                pageable: Pageable,
            ): Page<ModelArtifactCatalogReadModel> =
                findAll(pageable)

            override fun findById(id: UUID): ModelArtifactCatalogReadModel? = null

            override fun findProjectionById(id: UUID): ModelArtifactCatalogReadModelProjection? =
                projections.firstOrNull { it.modelId == id }

            override fun save(projection: ModelArtifactCatalogReadModelProjection) = Unit
        }

    private fun personalizedModelRepository(
        vararg projections: PersonalizedModelCatalogReadModelProjection,
    ): PersonalizedModelCatalogReadModelRepository =
        object : PersonalizedModelCatalogReadModelRepository {
            override fun findAll(pageable: Pageable): Page<PersonalizedModelCatalogReadModel> =
                findAllByCriteria(null, pageable)

            override fun findAllByCriteria(
                criteria: PersonalizedModelCatalogReadModelCriteria?,
                pageable: Pageable,
            ): Page<PersonalizedModelCatalogReadModel> =
                PageImpl(projections.map { it.toReadModel() })

            override fun findById(id: UUID): PersonalizedModelCatalogReadModel? = null

            override fun findProjectionById(id: UUID): PersonalizedModelCatalogReadModelProjection? =
                projections.firstOrNull { it.personalizedModelId == id }

            override fun save(projection: PersonalizedModelCatalogReadModelProjection) = Unit
        }

    private fun PersonalizedModelCatalogReadModelProjection.toReadModel(): PersonalizedModelCatalogReadModel =
        PersonalizedModelCatalogReadModel(
            personalizedModelId = personalizedModelId,
            modelUpdateSubmissionId = modelUpdateSubmissionId,
            trainingJobId = trainingJobId,
            trainingRunConfigurationId = trainingRunConfigurationId,
            roundId = roundId,
            roundNumber = roundNumber,
            runtimeId = runtimeId,
            featureSchemaId = featureSchemaId,
            personalizationMode = personalizationMode,
            personalizationScope = personalizationScope,
            personalizedArtifactPolicy = personalizedArtifactPolicy,
            personalizationMetric = personalizationMetric,
            minimumPersonalizationLift = minimumPersonalizationLift,
            personalizationTargetType = personalizationTargetType,
            personalizationTargetId = personalizationTargetId,
            personalizationCohortId = personalizationCohortId,
            personalizationProfileId = personalizationProfileId,
            personalizedModelArtifactRef = personalizedModelArtifactRef,
            personalizedModelArtifactDigest = personalizedModelArtifactDigest,
            personalizedMetricsArtifactRef = personalizedMetricsArtifactRef,
            personalizedAccuracy = personalizedAccuracy,
            personalizationLift = personalizationLift,
            state = state,
            projectionUpdatedAt = projectionUpdatedAt,
            userId = userId,
            sessionId = sessionId,
            correlationId = correlationId,
            causationId = causationId,
            traceId = traceId,
            tenantId = tenantId,
        )

    private fun uuid(value: String): UUID = UUID.fromString(value)
}
