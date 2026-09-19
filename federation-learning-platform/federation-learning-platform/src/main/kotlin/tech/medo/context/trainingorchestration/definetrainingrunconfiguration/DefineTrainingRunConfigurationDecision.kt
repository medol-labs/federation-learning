package tech.medo.trainingorchestration.definetrainingrunconfiguration

import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand
import tech.medo.trainingorchestration.runtimeengineprofile.RuntimeEngineProfileState

import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState





interface DefineTrainingRunConfigurationDecision {
    fun decide(command: DefineTrainingRunConfigurationCommand, state: RuntimeEngineProfileState): List<Any> {
        return listOf(
            TrainingRunConfigurationDefinedEvent(trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, federationId = command.federationId, federationName = command.federationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, initialModelId = command.initialModelId, initialModelName = command.initialModelName, initialModelPlugin = command.initialModelPlugin, initialModelVersion = command.initialModelVersion, initialModelArtifactUri = "" /* TODO: Snapshot the initial model artifact URI selected from the model repository. */, initialModelRegistryRef = "" /* TODO: Snapshot the model registry reference so runtime agents can identify where the initial global model artifact is managed. Runtime-engine plugin selection is carried separately by initialModelPlugin. */, initialModelFormat = "" /* TODO: Snapshot the model format needed by the runtime engine. */, initialModelArtifactDigest = "" /* TODO: Snapshot the artifact digest used by runtime agents to verify the pulled model. */, initialModelSignatureUri = null /* TODO: Snapshot the optional signature URI used to verify model provenance. */, runtimeEngineProfileId = command.runtimeEngineProfileId, runtimeEngineProfileName = command.runtimeEngineProfileName, runtimeEnginePluginProfile = requireNotNull(state.pluginProfile) { "runtimeEnginePluginProfile is required from state." }, runtimeEngineImage = requireNotNull(state.runtimeEngineImage) { "runtimeEngineImage is required from state." }, runtimeEngineImageDigest = state.imageDigest, strategyName = command.strategyName, aggregationAlgorithm = command.aggregationAlgorithm, maxRounds = command.maxRounds, minimumNodesPerRound = command.minimumNodesPerRound, roundTimeoutSeconds = command.roundTimeoutSeconds, nodeResponseTimeoutSeconds = command.nodeResponseTimeoutSeconds, localEpochs = command.localEpochs, batchSize = command.batchSize, learningRate = command.learningRate, optimizer = command.optimizer, lossFunction = command.lossFunction, gradientClippingNorm = command.gradientClippingNorm, secureAggregationRequired = command.secureAggregationRequired, minimumAccuracy = command.minimumAccuracy, minimumFairnessScore = command.minimumFairnessScore)
        )
    }
}
