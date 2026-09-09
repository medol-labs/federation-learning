package tech.medo.domain.trainingorchestration.definetrainingrunconfiguration

import org.springframework.stereotype.Component
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationDecision
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand
import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent

@Component
class DefineTrainingRunConfigurationDecisionComponent(
    private val modelArtifactCatalog: ModelArtifactCatalogReadModelRepository,
) : DefineTrainingRunConfigurationDecision {
    override fun decide(command: DefineTrainingRunConfigurationCommand): List<Any> {
        val model = modelArtifactCatalog.findProjectionById(command.initialModelId)
            ?: error("Initial model ${command.initialModelId} is required to define a training run configuration.")

        return listOf(
            TrainingRunConfigurationDefinedEvent(
                trainingRunConfigurationId = command.trainingRunConfigurationId,
                configurationName = command.configurationName,
                federationId = command.federationId,
                featureSchemaId = command.featureSchemaId,
                initialModelId = command.initialModelId,
                initialModelName = model.modelName.orEmpty(),
                initialModelVersion = model.modelVersion.orEmpty(),
                initialModelArtifactUri = model.modelArtifactUri.orEmpty(),
                initialModelRegistryRef = model.modelRegistryRef?.takeIf { it.isNotBlank() } ?: model.modelName.orEmpty(),
                initialModelFormat = model.modelFormat.orEmpty(),
                initialModelArtifactDigest = model.modelArtifactDigest.orEmpty(),
                initialModelSignatureUri = model.modelSignatureUri,
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
            ),
        )
    }
}
