package tech.medo.trainingorchestration.definetrainingrunconfiguration

import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand


import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState





interface DefineTrainingRunConfigurationDecision {
    fun decide(command: DefineTrainingRunConfigurationCommand): List<Any> {
        return listOf(
            TrainingRunConfigurationDefinedEvent(trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, federationId = command.federationId, federationName = command.federationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, initialModelId = command.initialModelId, initialModelName = command.initialModelName, initialModelVersion = command.initialModelVersion, initialModelArtifactUri = "" /* TODO: Snapshot the initial model artifact URI selected from the model repository. */, initialModelRegistryRef = "" /* TODO: Snapshot the model registry reference so runtime agents can identify where the initial global model is managed. For built-in runtime-engine models this value is the runtime engine model plugin code, copied from the selected ModelArtifact rather than read from runtime-agent environment variables. */, initialModelFormat = "" /* TODO: Snapshot the model format needed by the runtime engine. */, initialModelArtifactDigest = "" /* TODO: Snapshot the artifact digest used by runtime agents to verify the pulled model. */, initialModelSignatureUri = null /* TODO: Snapshot the optional signature URI used to verify model provenance. */, strategyName = command.strategyName, aggregationAlgorithm = command.aggregationAlgorithm, maxRounds = command.maxRounds, minimumNodesPerRound = command.minimumNodesPerRound, roundTimeoutSeconds = command.roundTimeoutSeconds, nodeResponseTimeoutSeconds = command.nodeResponseTimeoutSeconds, localEpochs = command.localEpochs, batchSize = command.batchSize, learningRate = command.learningRate, optimizer = command.optimizer, lossFunction = command.lossFunction, gradientClippingNorm = command.gradientClippingNorm, secureAggregationRequired = command.secureAggregationRequired, minimumAccuracy = command.minimumAccuracy, minimumFairnessScore = command.minimumFairnessScore)
        )
    }
}
