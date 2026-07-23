package tech.medo.trainingorchestration.updatetrainingrunconfiguration

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.updatetrainingrunconfiguration.UpdateTrainingRunConfigurationCommand

import tech.medo.trainingorchestration.events.TrainingRunConfigurationUpdatedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState


import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum


@Component
class UpdateTrainingRunConfigurationDecision {
    fun decide(command: UpdateTrainingRunConfigurationCommand, state: TrainingRunConfigurationState): List<Any> {
        require(state.currentState == TrainingRunConfigurationStateEnum.DRAFT) {
            "UpdateTrainingRunConfiguration requires TrainingRunConfiguration to be Draft."
        }
        return listOf(
            TrainingRunConfigurationUpdatedEvent(trainingRunConfigurationId = command.trainingRunConfigurationId, federationId = command.federationId, featureSchemaId = command.featureSchemaId, initialModelVersionId = command.initialModelVersionId, initialModelArtifactUri = "" /* TODO: Snapshot the updated initial model artifact URI selected from the model repository. */, initialModelRepositoryName = "" /* TODO: Snapshot the updated model repository name so runtime agents can identify where the initial global model is managed. */, initialModelFormat = "" /* TODO: Snapshot the updated model format needed by the runtime engine. */, initialModelHash = "" /* TODO: Snapshot the updated content hash used by runtime agents to verify the pulled model. */, initialModelSignatureUri = null /* TODO: Snapshot the updated optional signature URI used to verify model provenance. */, strategyName = command.strategyName, aggregationAlgorithm = command.aggregationAlgorithm, maxRounds = command.maxRounds, minimumNodesPerRound = command.minimumNodesPerRound, roundTimeoutSeconds = command.roundTimeoutSeconds, nodeResponseTimeoutSeconds = command.nodeResponseTimeoutSeconds, localEpochs = command.localEpochs, batchSize = command.batchSize, learningRate = command.learningRate, optimizer = command.optimizer, lossFunction = command.lossFunction, gradientClippingNorm = command.gradientClippingNorm, secureAggregationRequired = command.secureAggregationRequired, differentialPrivacyEnabled = command.differentialPrivacyEnabled, dpNoiseMultiplier = command.dpNoiseMultiplier, dpClipNorm = command.dpClipNorm, minimumAccuracy = command.minimumAccuracy, minimumFairnessScore = command.minimumFairnessScore, failureToleranceRatio = command.failureToleranceRatio, updateReason = command.updateReason)
        )
    }
}
