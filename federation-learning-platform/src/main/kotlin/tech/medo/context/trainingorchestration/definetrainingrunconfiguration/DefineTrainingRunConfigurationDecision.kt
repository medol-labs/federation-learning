package tech.medo.trainingorchestration.definetrainingrunconfiguration

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand

import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState





@Component
class DefineTrainingRunConfigurationDecision {
    fun decide(command: DefineTrainingRunConfigurationCommand): List<Any> {
        return listOf(
            TrainingRunConfigurationDefinedEvent(trainingRunConfigurationId = command.trainingRunConfigurationId, federationId = command.federationId, featureSchemaId = command.featureSchemaId, initialModelVersionId = command.initialModelVersionId, initialModelArtifactUri = "" /* TODO: Snapshot the initial model artifact URI selected from the model repository. */, initialModelRepositoryName = "" /* TODO: Snapshot the model repository name so runtime agents can identify where the initial global model is managed. */, initialModelFormat = "" /* TODO: Snapshot the model format needed by the runtime engine. */, initialModelHash = "" /* TODO: Snapshot the content hash used by runtime agents to verify the pulled model. */, initialModelSignatureUri = null /* TODO: Snapshot the optional signature URI used to verify model provenance. */, strategyName = command.strategyName, aggregationAlgorithm = command.aggregationAlgorithm, maxRounds = command.maxRounds, minimumNodesPerRound = command.minimumNodesPerRound, roundTimeoutSeconds = command.roundTimeoutSeconds, nodeResponseTimeoutSeconds = command.nodeResponseTimeoutSeconds, localEpochs = command.localEpochs, batchSize = command.batchSize, learningRate = command.learningRate, optimizer = command.optimizer, lossFunction = command.lossFunction, gradientClippingNorm = command.gradientClippingNorm, secureAggregationRequired = command.secureAggregationRequired, differentialPrivacyEnabled = command.differentialPrivacyEnabled, dpNoiseMultiplier = command.dpNoiseMultiplier, dpClipNorm = command.dpClipNorm, minimumAccuracy = command.minimumAccuracy, minimumFairnessScore = command.minimumFairnessScore, failureToleranceRatio = command.failureToleranceRatio)
        )
    }
}
