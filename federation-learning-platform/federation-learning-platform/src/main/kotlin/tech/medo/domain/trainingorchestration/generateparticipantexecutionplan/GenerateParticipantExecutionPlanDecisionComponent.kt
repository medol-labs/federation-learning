package tech.medo.domain.trainingorchestration.generateparticipantexecutionplan

import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import tech.jhipster.service.filter.StringFilter
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelProjection
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanDecision
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelCriteria
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModel
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelRepository
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelProjection
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelProjection
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import java.util.UUID

@Component
class GenerateParticipantExecutionPlanDecisionComponent(
    private val trainingRunConfigurationCatalog: TrainingRunConfigurationCatalogReadModelRepository,
    private val trainingRoundProgress: TrainingRoundProgressReadModelRepository,
    private val modelArtifactCatalog: ModelArtifactCatalogReadModelRepository,
    private val personalizedModelCatalog: PersonalizedModelCatalogReadModelRepository,
) : GenerateParticipantExecutionPlanDecision {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun decide(command: GenerateParticipantExecutionPlanCommand): List<Any> {
        val configuration = trainingRunConfigurationCatalog.findProjectionById(command.trainingRunConfigurationId)
            ?: error(
                "Training run configuration ${command.trainingRunConfigurationId} is required to generate participant execution plans.",
            )
        val baseModel = resolveBaseModel(command, configuration)
        val runtimeEngineProfile = resolveRuntimeEngineProfile(command, configuration)
        val personalizedBaseModel = resolvePersonalizedBaseModel(command)
        log.info(
            "Generated participant execution plan. trainingJobId={}, roundId={}, roundNumber={}, runtimeId={}, baseModelId={}, personalizedBaseModelId={}, artifactUri={}",
            command.trainingJobId,
            command.roundId,
            command.roundNumber,
            command.runtimeId,
            baseModel.baseModelId,
            personalizedBaseModel?.personalizedBaseModelId,
            baseModel.baseModelArtifactUri,
        )

        return listOf(
            ParticipantExecutionPlanGeneratedEvent(
                executionPlanId = command.executionPlanId,
                executionSessionId = command.executionSessionId,
                trainingJobId = command.trainingJobId,
                federationId = command.federationId,
                federationName = command.federationName,
                trainingRunConfigurationId = command.trainingRunConfigurationId,
                configurationName = command.configurationName,
                trainingJobObjective = command.trainingJobObjective,
                featureSchemaId = command.featureSchemaId,
                roundId = command.roundId,
                roundNumber = command.roundNumber,
                runtimeId = command.runtimeId,
                organizationId = command.organizationId,
                personalizationMode = command.personalizationMode,
                personalizationScope = command.personalizationScope,
                personalizedArtifactPolicy = command.personalizedArtifactPolicy,
                personalizationMetric = command.personalizationMetric,
                minimumPersonalizationLift = command.minimumPersonalizationLift,
                personalizationTargetType = command.personalizationTargetType,
                personalizationTargetId = command.personalizationTargetId,
                personalizationCohortId = command.personalizationCohortId,
                personalizationProfileId = command.personalizationProfileId,
                baseModelId = baseModel.baseModelId,
                baseModelArtifactUri = baseModel.baseModelArtifactUri,
                baseModelRegistryRef = baseModel.baseModelRegistryRef,
                baseModelPlugin = baseModel.baseModelPlugin,
                baseModelFormat = baseModel.baseModelFormat,
                baseModelArtifactDigest = baseModel.baseModelArtifactDigest,
                baseModelSignatureUri = baseModel.baseModelSignatureUri,
                personalizedBaseModelId = personalizedBaseModel?.personalizedBaseModelId,
                personalizedBaseModelArtifactUri = personalizedBaseModel?.personalizedBaseModelArtifactUri,
                personalizedBaseModelArtifactDigest = personalizedBaseModel?.personalizedBaseModelArtifactDigest,
                runtimeEngineProfileId = runtimeEngineProfile.runtimeEngineProfileId,
                runtimeEngineProfileName = runtimeEngineProfile.runtimeEngineProfileName,
                runtimeEnginePluginProfile = runtimeEngineProfile.runtimeEnginePluginProfile,
                runtimeEngineImage = runtimeEngineProfile.runtimeEngineImage,
                runtimeEngineImageDigest = runtimeEngineProfile.runtimeEngineImageDigest,
                secureAggregationRequired = command.secureAggregationRequired,
                secureAggregationSessionId = command.secureAggregationSessionId,
                encryptionScheme = command.encryptionScheme,
                publicKeyVersion = command.publicKeyVersion,
                publicKeyRef = command.publicKeyRef,
                encryptedParameterScale = command.encryptedParameterScale,
            ),
        )
    }

    private fun resolveBaseModel(
        command: GenerateParticipantExecutionPlanCommand,
        configuration: TrainingRunConfigurationCatalogReadModelProjection,
    ): BaseModelSnapshot {
        previousRoundSnapshot(command)?.let { return it }

        return BaseModelSnapshot(
            baseModelId = configuration.initialModelId
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide initialModelId."),
            baseModelArtifactUri = configuration.initialModelArtifactUri
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide initialModelArtifactUri."),
            baseModelRegistryRef = configuration.initialModelRegistryRef
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide initialModelRegistryRef."),
            baseModelPlugin = configuration.initialModelPlugin
                ?: error(
                    "TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide initialModelPlugin.",
                ),
            baseModelFormat = configuration.initialModelFormat
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide initialModelFormat."),
            baseModelArtifactDigest = configuration.initialModelArtifactDigest
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide initialModelArtifactDigest."),
            baseModelSignatureUri = configuration.initialModelSignatureUri,
        )
    }

    private fun resolveRuntimeEngineProfile(
        command: GenerateParticipantExecutionPlanCommand,
        configuration: TrainingRunConfigurationCatalogReadModelProjection,
    ): RuntimeEngineProfileSnapshot =
        RuntimeEngineProfileSnapshot(
            runtimeEngineProfileId = configuration.runtimeEngineProfileId
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide runtimeEngineProfileId."),
            runtimeEngineProfileName = configuration.runtimeEngineProfileName,
            runtimeEnginePluginProfile = configuration.runtimeEnginePluginProfile
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide runtimeEnginePluginProfile."),
            runtimeEngineImage = configuration.runtimeEngineImage
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide runtimeEngineImage."),
            runtimeEngineImageDigest = configuration.runtimeEngineImageDigest,
        )

    private fun previousRoundSnapshot(command: GenerateParticipantExecutionPlanCommand): BaseModelSnapshot? {
        if (command.roundNumber <= 1) {
            return null
        }

        val previousRound = trainingRoundProgress.findProjectionsByTrainingJobId(command.trainingJobId)
            .filter { projection ->
                val roundNumber = projection.roundNumber
                roundNumber != null && roundNumber < command.roundNumber
            }
            .filter { it.aggregatedModelId != null }
            .maxByOrNull { it.roundNumber ?: 0 }

        return previousRound?.toBaseModelSnapshot()
    }

    private fun TrainingRoundProgressReadModelProjection.toBaseModelSnapshot(): BaseModelSnapshot? {
        val modelId = aggregatedModelId ?: return null
        val model = modelArtifactCatalog.findProjectionById(modelId)
            ?: error("Aggregated model $modelId is required to generate the next round execution plan.")

        return model.toBaseModelSnapshot()
    }

    private fun ModelArtifactCatalogReadModelProjection.toBaseModelSnapshot(): BaseModelSnapshot =
        BaseModelSnapshot(
            baseModelId = modelId ?: error("ModelArtifactCatalog projection does not provide modelId."),
            baseModelArtifactUri = modelArtifactUri ?: error("ModelArtifactCatalog $modelId does not provide modelArtifactUri."),
            baseModelRegistryRef = modelRegistryRef ?: error("ModelArtifactCatalog $modelId does not provide modelRegistryRef."),
            baseModelPlugin = modelPlugin ?: error("ModelArtifactCatalog $modelId does not provide modelPlugin."),
            baseModelFormat = modelFormat ?: error("ModelArtifactCatalog $modelId does not provide modelFormat."),
            baseModelArtifactDigest = modelArtifactDigest ?: error("ModelArtifactCatalog $modelId does not provide modelArtifactDigest."),
            baseModelSignatureUri = modelSignatureUri,
        )

    private fun resolvePersonalizedBaseModel(command: GenerateParticipantExecutionPlanCommand): PersonalizedBaseModelSnapshot? {
        if (command.roundNumber <= 1 || command.personalizationMode.isNullOrBlank() || command.personalizationMode == "GLOBAL_ONLY") {
            return null
        }

        val criteria = PersonalizedModelCatalogReadModelCriteria().apply {
            trainingJobId = command.trainingJobId.asStringFilter()
            trainingRunConfigurationId = command.trainingRunConfigurationId.asStringFilter()
            runtimeId = command.runtimeId.asStringFilter()
            featureSchemaId = command.featureSchemaId.asStringFilter()
            personalizationTargetType = command.personalizationTargetType?.asStringFilter()
            personalizationTargetId = command.personalizationTargetId?.asStringFilter()
            personalizationCohortId = command.personalizationCohortId?.asStringFilter()
            personalizationProfileId = command.personalizationProfileId?.asStringFilter()
        }
        val pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "roundNumber"))

        return personalizedModelCatalog.findAllByCriteria(criteria, pageable)
            .content
            .asSequence()
            .filter { model -> (model.roundNumber ?: 0) < command.roundNumber }
            .firstOrNull()
            ?.toPersonalizedBaseModelSnapshot()
    }

    private fun PersonalizedModelCatalogReadModel.toPersonalizedBaseModelSnapshot(): PersonalizedBaseModelSnapshot =
        PersonalizedBaseModelSnapshot(
            personalizedBaseModelId = personalizedModelId
                ?: error("PersonalizedModelCatalog projection does not provide personalizedModelId."),
            personalizedBaseModelArtifactUri = personalizedModelArtifactRef
                ?: error("PersonalizedModelCatalog $personalizedModelId does not provide personalizedModelArtifactRef."),
            personalizedBaseModelArtifactDigest = personalizedModelArtifactDigest
                ?: error("PersonalizedModelCatalog $personalizedModelId does not provide personalizedModelArtifactDigest."),
        )

    private fun UUID.asStringFilter(): StringFilter = toString().asStringFilter()

    private fun String.asStringFilter(): StringFilter = StringFilter().apply {
        equals = this@asStringFilter
    }
}

private data class BaseModelSnapshot(
    val baseModelId: UUID,
    val baseModelArtifactUri: String,
    val baseModelRegistryRef: String,
    val baseModelPlugin: String,
    val baseModelFormat: String,
    val baseModelArtifactDigest: String,
    val baseModelSignatureUri: String?,
)

private data class PersonalizedBaseModelSnapshot(
    val personalizedBaseModelId: UUID,
    val personalizedBaseModelArtifactUri: String,
    val personalizedBaseModelArtifactDigest: String,
)

private data class RuntimeEngineProfileSnapshot(
    val runtimeEngineProfileId: UUID,
    val runtimeEngineProfileName: String?,
    val runtimeEnginePluginProfile: String,
    val runtimeEngineImage: String,
    val runtimeEngineImageDigest: String?,
)
