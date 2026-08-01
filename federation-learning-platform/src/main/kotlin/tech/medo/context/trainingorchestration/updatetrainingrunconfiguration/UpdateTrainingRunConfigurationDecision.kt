package tech.medo.trainingorchestration.updatetrainingrunconfiguration

import tech.medo.trainingorchestration.updatetrainingrunconfiguration.UpdateTrainingRunConfigurationCommand

import tech.medo.trainingorchestration.events.TrainingRunConfigurationUpdatedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState


import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum


interface UpdateTrainingRunConfigurationDecision {
    fun decide(command: UpdateTrainingRunConfigurationCommand, state: TrainingRunConfigurationState): List<Any> {
        require(state.currentState == TrainingRunConfigurationStateEnum.DRAFT) {
            "UpdateTrainingRunConfiguration requires TrainingRunConfiguration to be Draft."
        }
        return listOf(
            TrainingRunConfigurationUpdatedEvent(trainingRunConfigurationId = command.trainingRunConfigurationId, federationId = command.federationId, featureSchemaId = command.featureSchemaId, initialModelVersionId = command.initialModelVersionId, initialModelArtifactUri = requireNotNull(state.initialModelArtifactUri) { "initialModelArtifactUri is required from state." }, initialModelRepositoryName = requireNotNull(state.initialModelRepositoryName) { "initialModelRepositoryName is required from state." }, initialModelFormat = requireNotNull(state.initialModelFormat) { "initialModelFormat is required from state." }, initialModelHash = requireNotNull(state.initialModelHash) { "initialModelHash is required from state." }, initialModelSignatureUri = state.initialModelSignatureUri, strategyName = command.strategyName, aggregationAlgorithm = command.aggregationAlgorithm, maxRounds = command.maxRounds, minimumNodesPerRound = command.minimumNodesPerRound, roundTimeoutSeconds = command.roundTimeoutSeconds, nodeResponseTimeoutSeconds = command.nodeResponseTimeoutSeconds, localEpochs = command.localEpochs, batchSize = command.batchSize, learningRate = command.learningRate, optimizer = command.optimizer, lossFunction = command.lossFunction, gradientClippingNorm = command.gradientClippingNorm, secureAggregationRequired = command.secureAggregationRequired, differentialPrivacyEnabled = command.differentialPrivacyEnabled, dpNoiseMultiplier = command.dpNoiseMultiplier, dpClipNorm = command.dpClipNorm, minimumAccuracy = command.minimumAccuracy, minimumFairnessScore = command.minimumFairnessScore, failureToleranceRatio = command.failureToleranceRatio, updateReason = command.updateReason)
        )
    }
}
