package tech.medo.domain.trainingorchestration.updatetrainingrunconfiguration

import org.springframework.stereotype.Component
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum
import tech.medo.trainingorchestration.events.TrainingRunConfigurationUpdatedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState
import tech.medo.trainingorchestration.updatetrainingrunconfiguration.UpdateTrainingRunConfigurationCommand
import tech.medo.trainingorchestration.updatetrainingrunconfiguration.UpdateTrainingRunConfigurationDecision

@Component
class UpdateTrainingRunConfigurationDecisionComponent(
    private val modelArtifactCatalog: ModelArtifactCatalogReadModelRepository,
) : UpdateTrainingRunConfigurationDecision {
    override fun decide(command: UpdateTrainingRunConfigurationCommand, state: TrainingRunConfigurationState): List<Any> {
        require(state.currentState == TrainingRunConfigurationStateEnum.DRAFT) {
            "UpdateTrainingRunConfiguration requires TrainingRunConfiguration to be Draft."
        }
        val model = modelArtifactCatalog.findProjectionById(command.initialModelId)
            ?: error("Initial model ${command.initialModelId} is required to update a training run configuration.")

        return listOf(
            TrainingRunConfigurationUpdatedEvent(
                trainingRunConfigurationId = command.trainingRunConfigurationId,
                configurationName = command.configurationName,
                federationId = command.federationId,
                federationName = command.federationName,
                featureSchemaId = command.featureSchemaId,
                featureDomain = command.featureDomain,
                featureSchemaVersion = command.featureSchemaVersion,
                initialModelId = command.initialModelId,
                initialModelName = model.modelName.orEmpty(),
                initialModelPlugin = model.modelPlugin,
                initialModelVersion = model.modelVersion.orEmpty(),
                initialModelArtifactUri = model.modelArtifactUri.orEmpty(),
                initialModelRegistryRef = model.modelRegistryRef?.takeIf { it.isNotBlank() } ?: model.modelName.orEmpty(),
                initialModelFormat = model.modelFormat.orEmpty(),
                initialModelArtifactDigest = model.modelArtifactDigest.orEmpty(),
                initialModelSignatureUri = model.modelSignatureUri,
                runtimeEngineProfileId = command.runtimeEngineProfileId,
                runtimeEngineProfileName = command.runtimeEngineProfileName,
                runtimeEnginePluginProfile = command.runtimeEnginePluginProfile,
                runtimeEngineImage = command.runtimeEngineImage,
                runtimeEngineImageDigest = command.runtimeEngineImageDigest,
                strategyName = command.strategyName,
                aggregationAlgorithm = command.aggregationAlgorithm,
                maxRounds = command.maxRounds,
                minimumNodesPerRound = command.minimumNodesPerRound,
                roundTimeoutSeconds = command.roundTimeoutSeconds,
                nodeResponseTimeoutSeconds = command.nodeResponseTimeoutSeconds,
                localEpochs = command.localEpochs,
                batchSize = command.batchSize,
                learningRate = command.learningRate,
                optimizer = command.optimizer,
                lossFunction = command.lossFunction,
                gradientClippingNorm = command.gradientClippingNorm,
                secureAggregationRequired = command.secureAggregationRequired,
                minimumAccuracy = command.minimumAccuracy,
                minimumFairnessScore = command.minimumFairnessScore,
                updateReason = command.updateReason,
            ),
        )
    }
}
